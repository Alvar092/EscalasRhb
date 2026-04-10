package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetBergByIdUseCase @Inject constructor(
    private val repository: BergTestRepository
) {
    operator fun invoke(id: UUID): Flow<BergTest?> {
        return repository.getById(id)
    }
}