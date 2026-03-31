package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.MotricityIndexRepository
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import javax.inject.Inject

class SaveMotricityIndexUseCase @Inject constructor(
    private val repository: MotricityIndexRepository
)  {
    suspend operator fun invoke(motricityIndexTest: MotricityIndexTest) =
        repository.save(motricityIndexTest)
}