package com.aentrena.escalasrhb.mocks

import com.aentrena.escalasrhb.domain.interfaces.repositories.TrunkControlRepository
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID

class FakeTrunkControlRepository : TrunkControlRepository {

    private val tests = MutableStateFlow<List<TrunkControlTest>>(emptyList())

    override fun getAll(): Flow<List<TrunkControlTest>> = tests

    override fun getByPatient(patientId: UUID): Flow<List<TrunkControlTest>> =
        tests.map { it.filter { t -> t.patientId == patientId } }

    override fun getById(id: UUID): Flow<TrunkControlTest?> =
        tests.map { it.firstOrNull { t -> t.id == id } }

    override suspend fun save(test: TrunkControlTest) {
        val current = tests.value.toMutableList()
        val index = current.indexOfFirst { it.id == test.id }
        if (index >= 0) current[index] = test else current.add(test)
        tests.value = current
    }
}
