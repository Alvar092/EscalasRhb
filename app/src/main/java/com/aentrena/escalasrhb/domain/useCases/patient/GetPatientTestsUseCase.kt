package com.aentrena.escalasrhb.domain.useCases.patient

import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.MotricityIndexRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.TrunkControlRepository
import com.aentrena.escalasrhb.domain.model.patients.PatientHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID
import javax.inject.Inject

class GetPatientTestsUseCase @Inject constructor(
    private val patientRepository: PatientRepository,
    private val bergTestRepository: BergTestRepository,
    private val motricityIndexRepository: MotricityIndexRepository,
    private val trunkControlRepository: TrunkControlRepository
)  {
    operator fun invoke(patientId: UUID): Flow<PatientHistory> = combine(
        patientRepository.getById(patientId),
        bergTestRepository.getByPatient(patientId),
        motricityIndexRepository.getByPatient(patientId),
        trunkControlRepository.getByPatient(patientId)
    ) {
        patient, bergTests, motricityTests, trunkTests ->
        patient ?: throw PatientHistoryError.PatientNotFound
        PatientHistory(
            patient = patient,
            bergTests = bergTests,
            motricityIndexTests = motricityTests,
            trunkControlTests = trunkTests
        )
    }
}

sealed class PatientHistoryError : Exception() {
    data object PatientNotFound : PatientHistoryError()
    data object TestNotFound : PatientHistoryError()
}