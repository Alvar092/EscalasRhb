package com.aentrena.escalasrhb.domain.useCases.patient

import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import com.aentrena.escalasrhb.domain.model.patients.Patient
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetPatientByIdUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    operator fun invoke(id: UUID): Flow<Patient?> =
        repository.getById(id)
}