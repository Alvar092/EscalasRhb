package com.aentrena.escalasrhb.domain.useCases.patient

import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import com.aentrena.escalasrhb.domain.model.patients.Patient
import javax.inject.Inject

class EditPatientUseCase @Inject constructor(
    private val repository: PatientRepository
)  {
    suspend operator fun invoke(patient: Patient) =
        repository.update(patient)
}