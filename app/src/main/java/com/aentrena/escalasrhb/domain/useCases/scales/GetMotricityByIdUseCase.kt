package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.repositories.MotricityIndexRepository
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetMotricityByIdUseCase @Inject constructor(
    private val repository: MotricityIndexRepository
) {
    operator fun invoke(id: UUID): Flow<MotricityIndexTest?> {
        return repository.getById(id)
    }
}