package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergItemType
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItem
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItemType
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlItemType
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTestItem
import com.aentrena.escalasrhb.mocks.FakeBergTestRepository
import com.aentrena.escalasrhb.mocks.FakeClinicalHistoryRepository
import com.aentrena.escalasrhb.mocks.FakeMotricityIndexRepository
import com.aentrena.escalasrhb.mocks.FakeTrunkControlRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class ScalesUseCasesTest {

    // region Helpers

    private fun bergTest(
        id: UUID = UUID.randomUUID(),
        patientId: UUID = UUID.randomUUID()
    ) = BergTest(
        id = id,
        date = 0L,
        patientId = patientId,
        side = null,
        items = BergItemType.entries.map { BergItem(itemType = it) }
    )

    private fun motricityTest(
        id: UUID = UUID.randomUUID(),
        patientId: UUID = UUID.randomUUID()
    ) = MotricityIndexTest(
        id = id,
        date = 0L,
        patientId = patientId,
        items = MotricityIndexItemType.entries.map { MotricityIndexItem(itemType = it) }
    )

    private fun trunkTest(
        id: UUID = UUID.randomUUID(),
        patientId: UUID = UUID.randomUUID()
    ) = TrunkControlTest(
        id = id,
        date = 0L,
        patientId = patientId,
        items = TrunkControlItemType.entries.map { TrunkControlTestItem(itemType = it) }
    )

    // endregion

    // region CreateTestUseCase

    @Test
    fun `given BERG type, when created, then returns BergTest with all 14 items`() = runTest {
        // GIVEN
        val useCase = CreateTestUseCase(FakeBergTestRepository(), FakeMotricityIndexRepository(), FakeTrunkControlRepository())
        val patientId = UUID.randomUUID()

        // WHEN
        val result = useCase(TestType.BERG, patientId)

        // THEN
        assertTrue(result is BergTest)
        assertEquals(BergItemType.entries.size, result.items.size)
    }

    @Test
    fun `given MOTRICITY_INDEX type, when created, then returns MotricityIndexTest with all 6 items`() = runTest {
        // GIVEN
        val useCase = CreateTestUseCase(FakeBergTestRepository(), FakeMotricityIndexRepository(), FakeTrunkControlRepository())
        val patientId = UUID.randomUUID()

        // WHEN
        val result = useCase(TestType.MOTRICITY_INDEX, patientId)

        // THEN
        assertTrue(result is MotricityIndexTest)
        assertEquals(MotricityIndexItemType.entries.size, result.items.size)
    }

    @Test
    fun `given TRUNK_CONTROL_TEST type, when created, then returns TrunkControlTest with all 4 items`() = runTest {
        // GIVEN
        val useCase = CreateTestUseCase(FakeBergTestRepository(), FakeMotricityIndexRepository(), FakeTrunkControlRepository())
        val patientId = UUID.randomUUID()

        // WHEN
        val result = useCase(TestType.TRUNK_CONTROL_TEST, patientId)

        // THEN
        assertTrue(result is TrunkControlTest)
        assertEquals(TrunkControlItemType.entries.size, result.items.size)
    }

    @Test
    fun `given BERG type, when created, then test is persisted in berg repository`() = runTest {
        // GIVEN
        val bergRepository = FakeBergTestRepository()
        val useCase = CreateTestUseCase(bergRepository, FakeMotricityIndexRepository(), FakeTrunkControlRepository())
        val patientId = UUID.randomUUID()

        // WHEN
        val result = useCase(TestType.BERG, patientId)

        // THEN
        val stored = bergRepository.getAll().first()
        assertEquals(1, stored.size)
        assertEquals(result.id, stored.first().id)
    }

    // endregion

    // region SaveBergTestUseCase

    @Test
    fun `given a BergTest, when saved, then stored in repository`() = runTest {
        // GIVEN
        val repository = FakeBergTestRepository()
        val useCase = SaveBergTestUseCase(repository)
        val test = bergTest()

        // WHEN
        useCase(test)

        // THEN
        assertEquals(listOf(test), repository.getAll().first())
    }

    @Test
    fun `given an existing BergTest, when saved with updated scores, then repository reflects the changes`() = runTest {
        // GIVEN
        val repository = FakeBergTestRepository()
        val useCase = SaveBergTestUseCase(repository)
        val id = UUID.randomUUID()
        useCase(bergTest(id = id))
        val updated = bergTest(id = id).copy(
            items = BergItemType.entries.map { BergItem(itemType = it, score = 4) }
        )

        // WHEN
        useCase(updated)

        // THEN
        val stored = repository.getById(id).first()
        assertEquals(56, stored?.totalScore)
    }

    // endregion

    // region SaveMotricityIndexUseCase

    @Test
    fun `given a MotricityIndexTest, when saved, then stored in repository`() = runTest {
        // GIVEN
        val repository = FakeMotricityIndexRepository()
        val useCase = SaveMotricityIndexUseCase(repository)
        val test = motricityTest()

        // WHEN
        useCase(test)

        // THEN
        assertEquals(listOf(test), repository.getAll().first())
    }

    // endregion

    // region SaveTrunkControlTestUseCase

    @Test
    fun `given a TrunkControlTest, when saved, then stored in repository`() = runTest {
        // GIVEN
        val repository = FakeTrunkControlRepository()
        val useCase = SaveTrunkControlTestUseCase(repository)
        val test = trunkTest()

        // WHEN
        useCase(test)

        // THEN
        assertEquals(listOf(test), repository.getAll().first())
    }

    // endregion

    // region GetBergByIdUseCase

    @Test
    fun `given a BergTest in repository, when requested by id, then emits that test`() = runTest {
        // GIVEN
        val repository = FakeBergTestRepository()
        val id = UUID.randomUUID()
        val test = bergTest(id = id)
        repository.save(test)
        val useCase = GetBergByIdUseCase(repository)

        // WHEN
        val result = useCase(id).first()

        // THEN
        assertEquals(test, result)
    }

    @Test
    fun `given no BergTest with that id, when requested, then emits null`() = runTest {
        // GIVEN
        val useCase = GetBergByIdUseCase(FakeBergTestRepository())

        // WHEN
        val result = useCase(UUID.randomUUID()).first()

        // THEN
        assertNull(result)
    }

    // endregion

    // region GetMotricityByIdUseCase

    @Test
    fun `given a MotricityIndexTest in repository, when requested by id, then emits that test`() = runTest {
        // GIVEN
        val repository = FakeMotricityIndexRepository()
        val id = UUID.randomUUID()
        val test = motricityTest(id = id)
        repository.save(test)
        val useCase = GetMotricityByIdUseCase(repository)

        // WHEN
        val result = useCase(id).first()

        // THEN
        assertEquals(test, result)
    }

    @Test
    fun `given no MotricityIndexTest with that id, when requested, then emits null`() = runTest {
        // GIVEN
        val useCase = GetMotricityByIdUseCase(FakeMotricityIndexRepository())

        // WHEN
        val result = useCase(UUID.randomUUID()).first()

        // THEN
        assertNull(result)
    }

    // endregion

    // region GetTrunkControlByIdUseCase

    @Test
    fun `given a TrunkControlTest in repository, when requested by id, then emits that test`() = runTest {
        // GIVEN
        val repository = FakeTrunkControlRepository()
        val id = UUID.randomUUID()
        val test = trunkTest(id = id)
        repository.save(test)
        val useCase = GetTrunkControlByIdUseCase(repository)

        // WHEN
        val result = useCase(id).first()

        // THEN
        assertEquals(test, result)
    }

    @Test
    fun `given no TrunkControlTest with that id, when requested, then emits null`() = runTest {
        // GIVEN
        val useCase = GetTrunkControlByIdUseCase(FakeTrunkControlRepository())

        // WHEN
        val result = useCase(UUID.randomUUID()).first()

        // THEN
        assertNull(result)
    }

    // endregion

    // region GetTestResultUseCase

    @Test
    fun `given a BERG entry in history, when requested by id, then emits the BergTest`() = runTest {
        // GIVEN
        val id = UUID.randomUUID()
        val patientId = UUID.randomUUID()
        val historyRepo = FakeClinicalHistoryRepository()
        val bergRepo = FakeBergTestRepository()
        val test = bergTest(id = id, patientId = patientId)
        historyRepo.add(ClinicalHistory(id = id.toString(), patientId = patientId.toString(), testType = TestType.BERG, date = 0L, totalScore = 0, maxScore = 56))
        bergRepo.save(test)
        val useCase = GetTestResultUseCase(historyRepo, bergRepo, FakeMotricityIndexRepository(), FakeTrunkControlRepository())

        // WHEN
        val result = useCase(id).first()

        // THEN
        assertNotNull(result)
        assertEquals(id, result?.id)
        assertTrue(result is BergTest)
    }

    @Test
    fun `given a MOTRICITY_INDEX entry in history, when requested by id, then emits the MotricityIndexTest`() = runTest {
        // GIVEN
        val id = UUID.randomUUID()
        val patientId = UUID.randomUUID()
        val historyRepo = FakeClinicalHistoryRepository()
        val motricityRepo = FakeMotricityIndexRepository()
        val test = motricityTest(id = id, patientId = patientId)
        historyRepo.add(ClinicalHistory(id = id.toString(), patientId = patientId.toString(), testType = TestType.MOTRICITY_INDEX, date = 0L, totalScore = 0, maxScore = 100))
        motricityRepo.save(test)
        val useCase = GetTestResultUseCase(historyRepo, FakeBergTestRepository(), motricityRepo, FakeTrunkControlRepository())

        // WHEN
        val result = useCase(id).first()

        // THEN
        assertNotNull(result)
        assertEquals(id, result?.id)
        assertTrue(result is MotricityIndexTest)
    }

    @Test
    fun `given a TRUNK_CONTROL_TEST entry in history, when requested by id, then emits the TrunkControlTest`() = runTest {
        // GIVEN
        val id = UUID.randomUUID()
        val patientId = UUID.randomUUID()
        val historyRepo = FakeClinicalHistoryRepository()
        val trunkRepo = FakeTrunkControlRepository()
        val test = trunkTest(id = id, patientId = patientId)
        historyRepo.add(ClinicalHistory(id = id.toString(), patientId = patientId.toString(), testType = TestType.TRUNK_CONTROL_TEST, date = 0L, totalScore = 0, maxScore = 100))
        trunkRepo.save(test)
        val useCase = GetTestResultUseCase(historyRepo, FakeBergTestRepository(), FakeMotricityIndexRepository(), trunkRepo)

        // WHEN
        val result = useCase(id).first()

        // THEN
        assertNotNull(result)
        assertEquals(id, result?.id)
        assertTrue(result is TrunkControlTest)
    }

    @Test
    fun `given no entry in history, when requested, then emits null`() = runTest {
        // GIVEN
        val useCase = GetTestResultUseCase(FakeClinicalHistoryRepository(), FakeBergTestRepository(), FakeMotricityIndexRepository(), FakeTrunkControlRepository())

        // WHEN
        val result = useCase(UUID.randomUUID()).first()

        // THEN
        assertNull(result)
    }

    // endregion
}
