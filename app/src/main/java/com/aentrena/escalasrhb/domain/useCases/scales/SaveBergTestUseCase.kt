package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import javax.inject.Inject

class SaveBergTestUseCase @Inject constructor(
    private val repository: BergTestRepository
) {
    suspend operator fun invoke(bergTest: BergTest) =
        repository.save(bergTest)
}