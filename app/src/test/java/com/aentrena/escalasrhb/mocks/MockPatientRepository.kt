package com.aentrena.escalasrhb.mocks

import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import com.aentrena.escalasrhb.domain.model.patients.Patient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.UUID

class MockPatientRepository: PatientRepository {
    private val patients = MutableStateFlow<List<Patient>>(emptyList())

    override fun getAll(): Flow<List<Patient>> = patients

    override fun getById(id: UUID): Flow<Patient?> =
        patients.map {it.firstOrNull {p -> p.id == id }}

    override suspend fun save(patient: Patient) {
        val current = patients.value.toMutableList()
        val index = current.indexOfFirst { it.id == patient.id }
        if (index >= 0) current[index] = patient else current.add(patient)
        patients.value = current
    }
    override suspend fun update(patient: Patient) = save(patient)

    override suspend fun delete(id: UUID) {
        patients.value = patients.value.filter { it.id != id }
    }
}

val mockPatientRepository = MockPatientRepository().apply {
    runBlocking {
        save(
            Patient(
                id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                name = "Juan Pérez",
                dateOfBirth = System.currentTimeMillis()
            )
        )
        save(
            Patient(
                id = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                name = "Ana García",
                dateOfBirth = System.currentTimeMillis()
            )
        )
    }
}