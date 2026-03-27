package com.aentrena.escalasrhb.domain.model.scales

import com.aentrena.escalasrhb.domain.model.scales.BodySide
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTestItem
import com.aentrena.escalasrhb.domain.interfaces.SideTest
import java.util.UUID

class MotricityIndexTest(
    override val id: UUID = UUID.randomUUID(),
    override val date: Long,
    override val evaluator: String? = null,
    override val patientId: UUID,
    override val side: BodySide? = null,
    override val maxScore: Int? = null,
    override val items: List<MotricityIndexItem>
): ClinicalTest, SideTest {
    override val totalScore: Int
        get() = (upperLimbScore + lowerLimbScore) / 2

    val upperLimbScore: Int
        get() = items.filter { it.itemType.isUpperLimb }
            .sumOf { it.score ?: 0 } + 1

    val lowerLimbScore: Int
        get() = items.filter { it.itemType.isLowerLimb }
            .sumOf { it.score ?: 0 } + 1
}

data class MotricityIndexItem(
    override val id: UUID = UUID.randomUUID(),
    val itemType: MotricityIndexItemType,
    override val score: Int? = null
) : ClinicalTestItem

enum class MotricityIndexItemType {
    // Upper limb
    PINCH_GRIP,
    ELBOW_FLEXION,
    SHOULDER_ABDUCTION,
    // Lower limb
    HIP_FLEXION,
    KNEE_EXTENSION,
    ANKLE_DORSIFLEXION;

    val isUpperLimb: Boolean
        get() = this in listOf(PINCH_GRIP, ELBOW_FLEXION, SHOULDER_ABDUCTION)

    val isLowerLimb: Boolean
        get() = !isUpperLimb
}