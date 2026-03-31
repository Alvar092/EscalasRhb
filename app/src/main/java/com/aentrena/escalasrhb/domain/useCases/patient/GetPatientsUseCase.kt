package com.aentrena.escalasrhb.domain.useCases.patient

import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import com.aentrena.escalasrhb.domain.model.patients.Patient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPatientsUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    operator fun invoke(): Flow<List<Patient>> =
        repository.getAll()
}