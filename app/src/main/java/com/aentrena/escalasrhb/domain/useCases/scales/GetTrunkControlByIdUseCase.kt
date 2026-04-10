package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.repositories.TrunkControlRepository
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetTrunkControlByIdUseCase @Inject constructor(
    private val repository: TrunkControlRepository
) {
    operator fun invoke(id: UUID): Flow<TrunkControlTest?> {
        return repository.getById(id)
    }
}