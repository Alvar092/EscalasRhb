package com.aentrena.escalasrhb.domain.interfaces.repositories

import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TrunkControlRepository {
    fun getAll(): Flow<List<TrunkControlTest>>
    fun getByPatient(patientId: UUID): Flow<List<TrunkControlTest>>
    fun getById(id: UUID): Flow<TrunkControlTest?>
    suspend fun save(test: TrunkControlTest)
}