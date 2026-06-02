package com.aentrena.escalasrhb.domain.useCases.patient

import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import com.aentrena.escalasrhb.mocks.FakeClinicalHistoryRepository
import com.aentrena.escalasrhb.mocks.MockPatientRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class PatientUseCasesTest {

    // region CreatePatientUseCase

    @Test
    fun `given a patient, when created, then patient is stored in the repository`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val useCase = CreatePatientUseCase(repository)
        val patient = Patient(name = "Juan Pérez", dateOfBirth = 0L)

        // WHEN
        useCase(patient)

        // THEN
        val stored = repository.getAll().first()
        assertEquals(listOf(patient), stored)
    }

    @Test
    fun `given multiple patients, when each is created, then all are stored`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val useCase = CreatePatientUseCase(repository)
        val patients = listOf(
            Patient(name = "Juan Pérez", dateOfBirth = 0L),
            Patient(name = "Ana García", dateOfBirth = 0L),
        )

        // WHEN
        patients.forEach { useCase(it) }

        // THEN
        assertEquals(patients, repository.getAll().first())
    }

    @Test
    fun `given a patient with a specific id, when created, then that exact id is preserved`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val useCase = CreatePatientUseCase(repository)
        val id = UUID.fromString("00000000-0000-0000-0000-000000000099")
        val patient = Patient(id = id, name = "Test Patient", dateOfBirth = 0L)

        // WHEN
        useCase(patient)

        // THEN
        assertEquals(id, repository.getAll().first().first().id)
    }

    // endregion

    // region EditPatientUseCase

    @Test
    fun `given an existing patient, when edited with a new name, then repository reflects the change`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val id = UUID.randomUUID()
        repository.save(Patient(id = id, name = "Juan Pérez", dateOfBirth = 0L))
        val useCase = EditPatientUseCase(repository)

        // WHEN
        useCase(Patient(id = id, name = "Juan García", dateOfBirth = 0L))

        // THEN
        assertEquals("Juan García", repository.getById(id).first()?.name)
    }

    @Test
    fun `given two patients, when one is edited, then the other remains unchanged`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val targetId = UUID.randomUUID()
        val otherId = UUID.randomUUID()
        repository.save(Patient(id = targetId, name = "Target", dateOfBirth = 0L))
        repository.save(Patient(id = otherId, name = "Other", dateOfBirth = 0L))
        val useCase = EditPatientUseCase(repository)

        // WHEN
        useCase(Patient(id = targetId, name = "Target Updated", dateOfBirth = 0L))

        // THEN
        assertEquals("Other", repository.getById(otherId).first()?.name)
    }

    // endregion

    // region DeletePatientUseCase

    @Test
    fun `given a stored patient, when deleted by id, then patient is removed from repository`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val id = UUID.randomUUID()
        repository.save(Patient(id = id, name = "Juan Pérez", dateOfBirth = 0L))
        val useCase = DeletePatientUseCase(repository)

        // WHEN
        useCase(id)

        // THEN
        assertTrue(repository.getAll().first().isEmpty())
    }

    @Test
    fun `given two patients, when one is deleted, then the other remains`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val deleteId = UUID.randomUUID()
        val keepId = UUID.randomUUID()
        repository.save(Patient(id = deleteId, name = "To Delete", dateOfBirth = 0L))
        repository.save(Patient(id = keepId, name = "To Keep", dateOfBirth = 0L))
        val useCase = DeletePatientUseCase(repository)

        // WHEN
        useCase(deleteId)

        // THEN
        val stored = repository.getAll().first()
        assertEquals(1, stored.size)
        assertEquals(keepId, stored.first().id)
    }

    @Test
    fun `given a non-existent id, when deleted, then repository remains unchanged`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val existingId = UUID.randomUUID()
        repository.save(Patient(id = existingId, name = "Existing", dateOfBirth = 0L))
        val useCase = DeletePatientUseCase(repository)

        // WHEN
        useCase(UUID.randomUUID())

        // THEN
        assertEquals(1, repository.getAll().first().size)
    }

    // endregion

    // region GetPatientsUseCase

    @Test
    fun `given an empty repository, when all patients are requested, then emits empty list`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val useCase = GetPatientsUseCase(repository)

        // WHEN
        val result = useCase().first()

        // THEN
        assertTrue(result.isEmpty())
    }

    @Test
    fun `given patients in repository, when all are requested, then emits all patients`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val patients = listOf(
            Patient(id = UUID.randomUUID(), name = "Juan Pérez", dateOfBirth = 0L),
            Patient(id = UUID.randomUUID(), name = "Ana García", dateOfBirth = 0L),
        )
        patients.forEach { repository.save(it) }
        val useCase = GetPatientsUseCase(repository)

        // WHEN
        val result = useCase().first()

        // THEN
        assertEquals(patients, result)
    }

    // endregion

    // region GetPatientByIdUseCase

    @Test
    fun `given a patient in repository, when requested by id, then emits that patient`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val id = UUID.randomUUID()
        val patient = Patient(id = id, name = "Juan Pérez", dateOfBirth = 0L)
        repository.save(patient)
        val useCase = GetPatientByIdUseCase(repository)

        // WHEN
        val result = useCase(id).first()

        // THEN
        assertEquals(patient, result)
    }

    @Test
    fun `given no patient with that id, when requested, then emits null`() = runTest {
        // GIVEN
        val repository = MockPatientRepository()
        val useCase = GetPatientByIdUseCase(repository)

        // WHEN
        val result = useCase(UUID.randomUUID()).first()

        // THEN
        assertNull(result)
    }

    // endregion

    // region GetPatientTestsUseCase

    @Test
    fun `given history for a patient, when requested, then emits that patient's entries`() = runTest {
        // GIVEN
        val patientId = UUID.randomUUID()
        val repository = FakeClinicalHistoryRepository()
        repository.add(
            ClinicalHistory(
                id = UUID.randomUUID().toString(),
                patientId = patientId.toString(),
                testType = TestType.BERG,
                date = 0L,
                totalScore = 40,
                maxScore = 56
            )
        )
        val useCase = GetPatientTestsUseCase(repository)

        // WHEN
        val result = useCase(patientId).first()

        // THEN
        assertEquals(1, result.size)
        assertEquals(patientId.toString(), result.first().patientId)
    }

    @Test
    fun `given history for multiple patients, when requested for one, then emits only that patient's entries`() = runTest {
        // GIVEN
        val patientId = UUID.randomUUID()
        val otherId = UUID.randomUUID()
        val repository = FakeClinicalHistoryRepository()
        repository.add(ClinicalHistory(id = "1", patientId = patientId.toString(), testType = TestType.BERG, date = 0L, totalScore = 40, maxScore = 56))
        repository.add(ClinicalHistory(id = "2", patientId = otherId.toString(), testType = TestType.MOTRICITY_INDEX, date = 0L, totalScore = 20, maxScore = 100))
        val useCase = GetPatientTestsUseCase(repository)

        // WHEN
        val result = useCase(patientId).first()

        // THEN
        assertEquals(1, result.size)
        assertEquals(patientId.toString(), result.first().patientId)
    }

    @Test
    fun `given no history for a patient, when requested, then emits empty list`() = runTest {
        // GIVEN
        val repository = FakeClinicalHistoryRepository()
        val useCase = GetPatientTestsUseCase(repository)

        // WHEN
        val result = useCase(UUID.randomUUID()).first()

        // THEN
        assertTrue(result.isEmpty())
    }

    // endregion
}
