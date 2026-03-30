package com.aentrena.escalasrhb.data.repositories

import com.aentrena.escalasrhb.data.local.daos.PatientDao
import com.aentrena.escalasrhb.data.local.mappers.PatientMapper.toDomain
import com.aentrena.escalasrhb.data.local.mappers.PatientMapper.toEntity
import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import com.aentrena.escalasrhb.domain.model.patients.Patient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import java.util.UUID

class PatientRepositoryImpl @Inject constructor(
    private val dao: PatientDao
): PatientRepository {
    override fun getAll(): Flow<List<Patient>> =
        dao.getAllPatients().map { entities ->
            entities.map {it.toDomain()}
        }

    override fun getById(id: UUID): Flow<Patient?> =
        dao.getPatientById(id.toString()).map { it?.toDomain() }

    override suspend fun save(patient: Patient) {
        dao.insertPatient(patient.toEntity())
    }

    override suspend fun update(patient: Patient) {
        dao.insertPatient(patient.toEntity())
    }

    override suspend fun delete(id: UUID) {
        val entity = dao.getPatientById(id.toString()).first()
            ?: throw Exception("Patient not found")
        dao.deletePatient(entity)
    }
}