package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.ClinicalHistoryRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.MotricityIndexRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.TrunkControlRepository
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class GetTestResultUseCase @Inject constructor(
    private val clinicalHistoryRepository: ClinicalHistoryRepository,
    private val bergTestRepository: BergTestRepository,
    private val motricityIndexRepository: MotricityIndexRepository,
    private val trunkControlRepository: TrunkControlRepository
) {
    operator fun invoke(testId: UUID): Flow<ClinicalTest?> {
        return clinicalHistoryRepository.getById(testId)
            .flatMapLatest { entry ->
                when (entry?.testType) {
                    TestType.BERG -> bergTestRepository.getById(testId)
                    TestType.MOTRICITY_INDEX -> motricityIndexRepository.getById(testId)
                    TestType.TRUNK_CONTROL_TEST -> trunkControlRepository.getById(testId)
                    null -> flowOf(null)
                }
            }
    }
}