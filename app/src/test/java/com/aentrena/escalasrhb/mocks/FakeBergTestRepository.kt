package com.aentrena.escalasrhb.mocks

import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID

class FakeBergTestRepository : BergTestRepository {

    private val tests = MutableStateFlow<List<BergTest>>(emptyList())

    override fun getAll(): Flow<List<BergTest>> = tests

    override fun getByPatient(patientId: UUID): Flow<List<BergTest>> =
        tests.map { it.filter { t -> t.patientId == patientId } }

    override fun getById(id: UUID): Flow<BergTest?> =
        tests.map { it.firstOrNull { t -> t.id == id } }

    override suspend fun save(test: BergTest) {
        val current = tests.value.toMutableList()
        val index = current.indexOfFirst { it.id == test.id }
        if (index >= 0) current[index] = test else current.add(test)
        tests.value = current
    }
}
