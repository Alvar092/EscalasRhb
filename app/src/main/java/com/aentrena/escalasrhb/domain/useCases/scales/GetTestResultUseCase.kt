package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.repositories.ClinicalHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.take
import java.util.UUID
import javax.inject.Inject

class GetTestResultUseCase @Inject constructor(
    private val getTestResult: ClinicalHistoryRepository
) {
    operator suspend fun invoke(testId: UUID): Flow<ClinicalTest> =
        getTestResult.getById(testId = testId)
}