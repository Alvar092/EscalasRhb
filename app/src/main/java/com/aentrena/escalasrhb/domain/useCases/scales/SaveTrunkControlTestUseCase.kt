package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.TrunkControlRepository
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergItemType
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.domain.model.scales.BodySide
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItem
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItemType
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlItemType
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTestItem
import javax.inject.Inject
import java.util.UUID

class SaveTrunkControlTestUseCase @Inject constructor(
    private val repository: TrunkControlRepository
) {
    suspend operator fun invoke(trunkControlTest: TrunkControlTest) =
        repository.save(trunkControlTest)
}