package com.aentrena.escalasrhb.presentation.trunkControlTest.resources

import com.aentrena.escalasrhb.R
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlItemType
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergScoreOption

object TrunkControlItemCatalog {



    val definitions : Map<TrunkControlItemType, TrunkControlItemDefinition> = mapOf(

        TrunkControlItemType.BALANCED_SITTING to TrunkControlItemDefinition(
            type = TrunkControlItemType.BALANCED_SITTING,
            titleRes = R.string.trunkControl_balancedSitting_title,
            descriptionRes = R.string.trunkControl_balancedSitting_description,
            scoringOptions = listOf(
                TrunkControlScoreOption(25, R.string.trunkControl_standard_score_25),
                TrunkControlScoreOption(12, R.string.trunkControl_standard_score_12),
                TrunkControlScoreOption(0, R.string.trunkControl_standard_score_0),
            ),
            needsTimer = false
        ),

        TrunkControlItemType.ROLLING_TO_STRONG_SIDE to TrunkControlItemDefinition(
            type = TrunkControlItemType.ROLLING_TO_STRONG_SIDE,
            titleRes = R.string.trunkControl_rollingToStrongSide_title,
            descriptionRes = R.string.trunkControl_rollingToStrongSide_description,
            scoringOptions = listOf(
                TrunkControlScoreOption(25, R.string.trunkControl_standard_score_25),
                TrunkControlScoreOption(12, R.string.trunkControl_standard_score_12),
                TrunkControlScoreOption(0, R.string.trunkControl_standard_score_0),
            ),
            needsTimer = false
        ),

        TrunkControlItemType.ROLLING_TO_WEAK_SIDE to TrunkControlItemDefinition(
            type = TrunkControlItemType.ROLLING_TO_WEAK_SIDE,
            titleRes = R.string.trunkControl_rollingToWeakSide_title,
            descriptionRes = R.string.trunkControl_rollingToWeakSide_description,
            scoringOptions = listOf(
                TrunkControlScoreOption(25, R.string.trunkControl_standard_score_25),
                TrunkControlScoreOption(12, R.string.trunkControl_standard_score_12),
                TrunkControlScoreOption(0, R.string.trunkControl_standard_score_0),
            ),
            needsTimer = false
        ),

        TrunkControlItemType.SITTING_UP_FROM_LYING_DOWN to TrunkControlItemDefinition(
            type = TrunkControlItemType.SITTING_UP_FROM_LYING_DOWN,
            titleRes = R.string.trunkControl_sittingUpFromLyingDown_title,
            descriptionRes = R.string.trunkControl_sittingUpFromLyingDown_description,
            scoringOptions = listOf(
                TrunkControlScoreOption(25, R.string.trunkControl_standard_score_25),
                TrunkControlScoreOption(12, R.string.trunkControl_standard_score_12),
                TrunkControlScoreOption(0, R.string.trunkControl_standard_score_0),
            ),
            needsTimer = false
        ),
    )

}