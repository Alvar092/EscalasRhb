package com.aentrena.escalasrhb.domain.model.scales

import com.aentrena.escalasrhb.domain.model.scales.BodySide
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTestItem
import com.aentrena.escalasrhb.domain.interfaces.SideTest
import java.util.UUID

class TrunckControlTest(
    override val id: UUID = UUID.randomUUID(),
    override val date: Long,
    override val evaluator: String? = null,
    override val patientId: UUID,
    override val side: BodySide? = null,
    override val maxScore: Int? = 100,
    override val items: List<TrunckControlTestItem>
): ClinicalTest, SideTest {
    override val totalScore: Int
        get() = items.sumOf { it.score ?: 0}
}

data class TrunckControlTestItem(
    override val id: UUID = UUID.randomUUID(),
    val itemType: TrunckControlItemType,
    override  val score: Int? = null
): ClinicalTestItem

enum class TrunckControlItemType {
    ROLLING_TO_WEAK_SIDE,
    ROLLING_TO_STRONG_SIDE,
    BALANCED_SITTING,
    SITTING_UP_FROM_LYING_DOWN
}