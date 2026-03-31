package com.aentrena.escalasrhb.domain.useCases.patient

import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import java.util.UUID
import javax.inject.Inject

class DeletePatientUseCase @Inject constructor(
    private val repository: PatientRepository
)  {
    suspend operator fun invoke(id: UUID) =
        repository.delete(id)
}