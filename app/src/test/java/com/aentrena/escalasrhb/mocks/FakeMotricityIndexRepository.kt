package com.aentrena.escalasrhb.mocks

import com.aentrena.escalasrhb.domain.interfaces.repositories.MotricityIndexRepository
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID

class FakeMotricityIndexRepository : MotricityIndexRepository {

    private val tests = MutableStateFlow<List<MotricityIndexTest>>(emptyList())

    override fun getAll(): Flow<List<MotricityIndexTest>> = tests

    override fun getByPatient(patientId: UUID): Flow<List<MotricityIndexTest>> =
        tests.map { it.filter { t -> t.patientId == patientId } }

    override fun getById(id: UUID): Flow<MotricityIndexTest?> =
        tests.map { it.firstOrNull { t -> t.id == id } }

    override suspend fun save(test: MotricityIndexTest) {
        val current = tests.value.toMutableList()
        val index = current.indexOfFirst { it.id == test.id }
        if (index >= 0) current[index] = test else current.add(test)
        tests.value = current
    }
}
