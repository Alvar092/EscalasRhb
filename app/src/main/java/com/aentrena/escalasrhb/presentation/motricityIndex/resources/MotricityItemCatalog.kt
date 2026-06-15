package com.aentrena.escalasrhb.presentation.motricityIndex.resources

import com.aentrena.escalasrhb.R
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItemType

object MotricityIndexCatalog {

    // Opciones comunes para los 5 items estándar
    private val standardScoringOptions = listOf(
        MotricityScoreOption(33, R.string.motricity_standard_score_33),
        MotricityScoreOption(25, R.string.motricity_standard_score_25),
        MotricityScoreOption(19, R.string.motricity_standard_score_19),
        MotricityScoreOption(14, R.string.motricity_standard_score_14),
        MotricityScoreOption(9, R.string.motricity_standard_score_9),
        MotricityScoreOption(0, R.string.motricity_standard_score_0),
    )

    private val pinchGripScoringOptions = listOf(
        MotricityScoreOption(33, R.string.motricity_pinchGrip_score_33),
        MotricityScoreOption(26, R.string.motricity_pinchGrip_score_26),
        MotricityScoreOption(22, R.string.motricity_pinchGrip_score_22),
        MotricityScoreOption(19, R.string.motricity_pinchGrip_score_19),
        MotricityScoreOption(11, R.string.motricity_pinchGrip_score_11),
        MotricityScoreOption(0, R.string.motricity_pinchGrip_score_0),
    )

    val definitions: Map<MotricityIndexItemType, MotricityItemDefinition> = mapOf(

        MotricityIndexItemType.PINCH_GRIP to MotricityItemDefinition(
            type = MotricityIndexItemType.PINCH_GRIP,
            titleRes = R.string.motricity_pinchGrip_title,
            descriptionRes = R.string.motricity_pinchGrip_description,
            scoringOptions = pinchGripScoringOptions,
            needsTimer = false
        ),

        MotricityIndexItemType.ELBOW_FLEXION to MotricityItemDefinition(
            type = MotricityIndexItemType.ELBOW_FLEXION,
            titleRes = R.string.motricity_elbowFlexion_title,
            descriptionRes = R.string.motricity_elbowFlexion_description,
            scoringOptions = standardScoringOptions,
            needsTimer = false
        ),

        MotricityIndexItemType.SHOULDER_ABDUCTION to MotricityItemDefinition(
            type = MotricityIndexItemType.SHOULDER_ABDUCTION,
            titleRes = R.string.motricity_shoulderAbduction_title,
            descriptionRes = R.string.motricity_shoulderAbduction_description,
            scoringOptions = standardScoringOptions,
            needsTimer = false
        ),

        MotricityIndexItemType.HIP_FLEXION to MotricityItemDefinition(
            type = MotricityIndexItemType.HIP_FLEXION,
            titleRes = R.string.motricity_hipFlexion_title,
            descriptionRes = R.string.motricity_hipFlexion_description,
            scoringOptions = standardScoringOptions,
            needsTimer = false
        ),

        MotricityIndexItemType.KNEE_EXTENSION to MotricityItemDefinition(
            type = MotricityIndexItemType.KNEE_EXTENSION,
            titleRes = R.string.motricity_kneeExtension_title,
            descriptionRes = R.string.motricity_kneeExtension_description,
            scoringOptions = standardScoringOptions,
            needsTimer = false
        ),

        MotricityIndexItemType.ANKLE_DORSIFLEXION to MotricityItemDefinition(
            type = MotricityIndexItemType.ANKLE_DORSIFLEXION,
            titleRes = R.string.motricity_ankleDorsiflexion_title,
            descriptionRes = R.string.motricity_ankleDorsiflexion_description,
            scoringOptions = standardScoringOptions,
            needsTimer = false
        ),

    )
}