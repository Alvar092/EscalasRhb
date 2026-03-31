package com.aentrena.escalasrhb.domain.useCases.scales

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
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
import java.util.UUID
import javax.inject.Inject

class CreateTestUseCase @Inject constructor() {
    operator fun invoke(
        type: TestType,
        patientId: UUID,
        side: BodySide? = null,
    ): ClinicalTest = when (type) {
        TestType.BERG -> BergTest(
            id = UUID.randomUUID(),
            date = System.currentTimeMillis(),
            patientId = patientId,
            items = BergItemType.entries.map { itemType ->
                BergItem(id = UUID.randomUUID(), itemType = itemType)
            }
        )

        TestType.MOTRICITY_INDEX -> MotricityIndexTest(
            id = UUID.randomUUID(),
            date = System.currentTimeMillis(),
            patientId = patientId,
            side = side,
            items = MotricityIndexItemType.entries.map { itemType ->
                MotricityIndexItem(id = UUID.randomUUID(), itemType = itemType)
            }
        )

        TestType.TRUNK_CONTROL_TEST -> TrunkControlTest(
            id = UUID.randomUUID(),
            date = System.currentTimeMillis(),
            patientId = patientId,
            items = TrunkControlItemType.entries.map { itemType ->
                TrunkControlTestItem(id = UUID.randomUUID(), itemType = itemType)
            }
        )
    }
}