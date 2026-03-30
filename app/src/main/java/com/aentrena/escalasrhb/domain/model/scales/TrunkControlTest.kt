package com.aentrena.escalasrhb.domain.model.scales

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTestItem
import com.aentrena.escalasrhb.domain.interfaces.SideTest
import java.util.UUID

class TrunkControlTest(
    override val id: UUID = UUID.randomUUID(),
    override val date: Long,
    override val evaluator: String? = null,
    override val patientId: UUID,
    override val side: BodySide? = null,
    override val maxScore: Int? = 100,
    override val items: List<TrunkControlTestItem>
): ClinicalTest, SideTest {
    override val totalScore: Int
        get() = items.sumOf { it.score ?: 0}
}

data class TrunkControlTestItem(
    override val id: UUID = UUID.randomUUID(),
    val itemType: TrunkControlItemType,
    override  val score: Int? = null
): ClinicalTestItem

enum class TrunkControlItemType {
    ROLLING_TO_WEAK_SIDE,
    ROLLING_TO_STRONG_SIDE,
    BALANCED_SITTING,
    SITTING_UP_FROM_LYING_DOWN
}