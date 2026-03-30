package com.aentrena.escalasrhb.domain.interfaces.repositories

import com.aentrena.escalasrhb.domain.model.patients.Patient
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PatientRepository {
    fun getAll(): Flow<List<Patient>>
    fun getById(id: UUID): Flow<Patient?>
    suspend fun save(patient: Patient)
    suspend fun update(patient: Patient)
    suspend fun delete(id: UUID)
}