package com.aentrena.escalasrhb.domain.model.scales

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTestItem
import java.util.UUID

data class BergTest(
    override val id: UUID = UUID.randomUUID(),
    override val date: Long,
    override val evaluator: String? = null,
    override val patientId: UUID,
    override val items: List<BergItem>,
    override val maxScore: Int = 56
): ClinicalTest {
    override val totalScore: Int
            get() = items.mapNotNull {it.score}.sum()
}

data class BergItem(
    override val id: UUID = UUID.randomUUID(),
    val itemType: BergItemType,
    override val score: Int? = null,
    val timeRecorded: Double? = null
) : ClinicalTestItem {

    fun withTimeScoring(): BergItem {
        val t = timeRecorded ?: return this
        val newScore = when (itemType) {
            BergItemType.STANDING_UNSUPPORTED -> when {
                t >= 120.0 -> 4
                t >= 30.0  -> 2
                else       -> 0
            }
            BergItemType.SITTING_WITH_BACK_UNSUPPORTED -> when {
                t >= 120.0 -> 4
                t >= 30.0  -> 2
                t >= 10.0  -> 1
                else       -> 0
            }
            BergItemType.STANDING_UNSUPPORTED_EYES_CLOSED -> when {
                t >= 10.0 -> 4
                t >= 3.0  -> 2
                else      -> 0
            }
            BergItemType.STANDING_UNSUPPORTED_FEET_TOGETHER -> when {
                t >= 60.0 -> 4
                t >= 30.0 -> 2
                t > 15.0  -> 1
                else      -> 0
            }
            BergItemType.TURN_360_DEGREES -> if (t <= 4.0) 4 else 2
            BergItemType.PLACING_ALTERNATE_FOOT_ON_STEP -> if (t <= 20.0) 4 else 3
            BergItemType.STANDING_ONE_FOOT_IN_FRONT -> when {
                t >= 30.0 -> 4
                t >= 15.0 -> 1
                else      -> 0
            }
            BergItemType.STANDING_ON_ONE_LEG -> when {
                t >= 10.0 -> 4
                t >= 5.0  -> 3
                t >= 3.0  -> 2
                else      -> 0
            }
            else -> return this // items sin tiempo no aplica
        }
        return BergItem(score = newScore.coerceIn(0, 4), itemType = itemType)
    }
}

enum class BergItemType {
    SITTING_TO_STANDING,
    STANDING_UNSUPPORTED,
    SITTING_WITH_BACK_UNSUPPORTED,
    STANDING_TO_SITTING,
    TRANSFERS,
    STANDING_UNSUPPORTED_EYES_CLOSED,
    STANDING_UNSUPPORTED_FEET_TOGETHER,
    REACHING_FORWARD_OUTSTRETCHED_ARM,
    PICK_UP_OBJECT_FROM_FLOOR,
    TURNING_TO_LOOK_BEHIND,
    TURN_360_DEGREES,
    PLACING_ALTERNATE_FOOT_ON_STEP,
    STANDING_ONE_FOOT_IN_FRONT,
    STANDING_ON_ONE_LEG
}
