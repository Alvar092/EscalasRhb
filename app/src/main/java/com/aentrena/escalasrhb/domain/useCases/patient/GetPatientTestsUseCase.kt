package com.aentrena.escalasrhb.domain.useCases.patient

import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.ClinicalHistoryRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.MotricityIndexRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.TrunkControlRepository
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID
import javax.inject.Inject

class GetPatientTestsUseCase @Inject constructor(
    private val clinicalHistoryRepository: ClinicalHistoryRepository
)  {
    operator fun invoke(patientId: UUID): Flow<List<ClinicalHistory>> {
        return clinicalHistoryRepository.getPatientHistory(patientId)
    }

}

