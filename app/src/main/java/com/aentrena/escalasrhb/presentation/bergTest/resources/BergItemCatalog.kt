package com.aentrena.escalasrhb.presentation.bergTest.resources

import com.aentrena.escalasrhb.R
import com.aentrena.escalasrhb.domain.model.scales.BergItemType

object BergItemCatalog {

    val definitions: Map<BergItemType, BergItemDefinition> = mapOf(
        // Item 1
        BergItemType.SITTING_TO_STANDING to BergItemDefinition(
            type = BergItemType.SITTING_TO_STANDING,
            titleRes = R.string.berg_sittingtostanding_title,
            descriptionRes = R.string.berg_sittingtostanding_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_sittingtostanding_score_0),
                BergScoreOption(3, R.string.berg_sittingtostanding_score_1),
                BergScoreOption(2, R.string.berg_sittingtostanding_score_2),
                BergScoreOption(1, R.string.berg_sittingtostanding_score_3),
                BergScoreOption(0, R.string.berg_sittingtostanding_score_4),
            ),
            needsTimer = false
        ),
        // Item 2
        BergItemType.STANDING_UNSUPPORTED to BergItemDefinition(
            type = BergItemType.STANDING_UNSUPPORTED,
            titleRes = R.string.berg_standingunsupported_title,
            descriptionRes = R.string.berg_standingunsupported_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_standingunsupported_score_0),
                BergScoreOption(3, R.string.berg_standingunsupported_score_1),
                BergScoreOption(2, R.string.berg_standingunsupported_score_2),
                BergScoreOption(1, R.string.berg_standingunsupported_score_3),
                BergScoreOption(0, R.string.berg_standingunsupported_score_4),
            ),
            needsTimer = true
        ),
        // Item 3
        BergItemType.SITTING_WITH_BACK_UNSUPPORTED to BergItemDefinition(
            type = BergItemType.SITTING_WITH_BACK_UNSUPPORTED,
            titleRes = R.string.berg_sittingwithbackunsupported_title,
            descriptionRes = R.string.berg_sittingwithbackunsupported_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_sittingwithbackunsupported_score_0),
                BergScoreOption(3, R.string.berg_sittingwithbackunsupported_score_1),
                BergScoreOption(2, R.string.berg_sittingwithbackunsupported_score_2),
                BergScoreOption(1, R.string.berg_sittingwithbackunsupported_score_3),
                BergScoreOption(0, R.string.berg_sittingwithbackunsupported_score_4),
            ),
            needsTimer = true
        ),
        // Item 4
        BergItemType.STANDING_TO_SITTING to BergItemDefinition(
            type = BergItemType.STANDING_TO_SITTING,
            titleRes = R.string.berg_standingtositting_title,
            descriptionRes = R.string.berg_standingtositting_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_standingtositting_score_0),
                BergScoreOption(3, R.string.berg_standingtositting_score_1),
                BergScoreOption(2, R.string.berg_standingtositting_score_2),
                BergScoreOption(1, R.string.berg_standingtositting_score_3),
                BergScoreOption(0, R.string.berg_standingtositting_score_4),
            ),
            needsTimer = false
        ),
        // Item 5
        BergItemType.TRANSFERS to BergItemDefinition(
            type = BergItemType.TRANSFERS,
            titleRes = R.string.berg_transfers_title,
            descriptionRes = R.string.berg_transfers_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_transfers_score_0),
                BergScoreOption(3, R.string.berg_transfers_score_1),
                BergScoreOption(2, R.string.berg_transfers_score_2),
                BergScoreOption(1, R.string.berg_transfers_score_3),
                BergScoreOption(0, R.string.berg_transfers_score_4),
            ),
            needsTimer = false
        ),
        // Item 6
        BergItemType.STANDING_UNSUPPORTED_EYES_CLOSED to BergItemDefinition(
            type = BergItemType.STANDING_UNSUPPORTED_EYES_CLOSED,
            titleRes = R.string.berg_standingunsupportedwitheyesclosed_title,
            descriptionRes = R.string.berg_standingunsupportedwitheyesclosed_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_standingunsupportedwitheyesclosed_score_0),
                BergScoreOption(3, R.string.berg_standingunsupportedwitheyesclosed_score_1),
                BergScoreOption(2, R.string.berg_standingunsupportedwitheyesclosed_score_2),
                BergScoreOption(1, R.string.berg_standingunsupportedwitheyesclosed_score_3),
                BergScoreOption(0, R.string.berg_standingunsupportedwitheyesclosed_score_4),
            ),
            needsTimer = true
        ),
        // Item 7
        BergItemType.STANDING_UNSUPPORTED_FEET_TOGETHER to BergItemDefinition(
            type = BergItemType.STANDING_UNSUPPORTED_FEET_TOGETHER,
            titleRes = R.string.berg_standingunsupportedwithfeettogether_title,
            descriptionRes = R.string.berg_standingunsupportedwithfeettogether_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_standingunsupportedwithfeettogether_score_0),
                BergScoreOption(3, R.string.berg_standingunsupportedwithfeettogether_score_1),
                BergScoreOption(2, R.string.berg_standingunsupportedwithfeettogether_score_2),
                BergScoreOption(1, R.string.berg_standingunsupportedwithfeettogether_score_3),
                BergScoreOption(0, R.string.berg_standingunsupportedwithfeettogether_score_4),
            ),
            needsTimer = true
        ),
        // Item 8
        BergItemType.REACHING_FORWARD_OUTSTRETCHED_ARM to BergItemDefinition(
            type = BergItemType.REACHING_FORWARD_OUTSTRETCHED_ARM,
            titleRes = R.string.berg_reachingforwardwithoutstretchedarmwhilestanding_title,
            descriptionRes = R.string.berg_reachingforwardwithoutstretchedarmwhilestanding_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_reachingforwardwithoutstretchedarmwhilestanding_score_0),
                BergScoreOption(3, R.string.berg_reachingforwardwithoutstretchedarmwhilestanding_score_1),
                BergScoreOption(2, R.string.berg_reachingforwardwithoutstretchedarmwhilestanding_score_2),
                BergScoreOption(1, R.string.berg_reachingforwardwithoutstretchedarmwhilestanding_score_3),
                BergScoreOption(0, R.string.berg_reachingforwardwithoutstretchedarmwhilestanding_score_4),
            ),
            needsTimer = false
        ),
        // Item 9
        BergItemType.PICK_UP_OBJECT_FROM_FLOOR to BergItemDefinition(
            type = BergItemType.PICK_UP_OBJECT_FROM_FLOOR,
            titleRes = R.string.berg_pickupobjectfromthefloorfromastandingposition_title,
            descriptionRes = R.string.berg_pickupobjectfromthefloorfromastandingposition_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_pickupobjectfromthefloorfromastandingposition_score_4),
                BergScoreOption(3, R.string.berg_pickupobjectfromthefloorfromastandingposition_score_3),
                BergScoreOption(2, R.string.berg_pickupobjectfromthefloorfromastandingposition_score_2),
                BergScoreOption(1, R.string.berg_pickupobjectfromthefloorfromastandingposition_score_1),
                BergScoreOption(0, R.string.berg_pickupobjectfromthefloorfromastandingposition_score_0),
            ),
            needsTimer = false
        ),
        // Item 10
        BergItemType.TURNING_TO_LOOK_BEHIND to BergItemDefinition(
            type = BergItemType.TURNING_TO_LOOK_BEHIND,
            titleRes = R.string.berg_turningtolookbehindoverleftandrightshoulders_title,
            descriptionRes = R.string.berg_turningtolookbehindoverleftandrightshoulders_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_turningtolookbehindoverleftandrightshoulders_score_4),
                BergScoreOption(3, R.string.berg_turningtolookbehindoverleftandrightshoulders_score_3),
                BergScoreOption(2, R.string.berg_turningtolookbehindoverleftandrightshoulders_score_2),
                BergScoreOption(1, R.string.berg_turningtolookbehindoverleftandrightshoulders_score_1),
                BergScoreOption(0, R.string.berg_turningtolookbehindoverleftandrightshoulders_score_0),
            ),
            needsTimer = false
        ),
        // Item 11
        BergItemType.TURN_360_DEGREES to BergItemDefinition(
            type = BergItemType.TURN_360_DEGREES,
            titleRes = R.string.berg_turn360degrees_title,
            descriptionRes = R.string.berg_turn360degrees_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_turn360degrees_score_4),
                BergScoreOption(3, R.string.berg_turn360degrees_score_3),
                BergScoreOption(2, R.string.berg_turn360degrees_score_2),
                BergScoreOption(1, R.string.berg_turn360degrees_score_1),
                BergScoreOption(0, R.string.berg_turn360degrees_score_0),
            ),
            needsTimer = true
        ),
        // Item 12
        BergItemType.PLACING_ALTERNATE_FOOT_ON_STEP to BergItemDefinition(
            type = BergItemType.PLACING_ALTERNATE_FOOT_ON_STEP,
            titleRes = R.string.berg_placingalternatesfootonsteporstoolwhilestandingunsupported_title,
            descriptionRes = R.string.berg_placingalternatesfootonsteporstoolwhilestandingunsupported_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_placingalternatesfootonsteporstoolwhilestandingunsupported_score_4),
                BergScoreOption(3, R.string.berg_placingalternatesfootonsteporstoolwhilestandingunsupported_score_3),
                BergScoreOption(2, R.string.berg_placingalternatesfootonsteporstoolwhilestandingunsupported_score_2),
                BergScoreOption(1, R.string.berg_placingalternatesfootonsteporstoolwhilestandingunsupported_score_1),
                BergScoreOption(0, R.string.berg_placingalternatesfootonsteporstoolwhilestandingunsupported_score_0),
            ),
            needsTimer = true
        ),
        // Item 13
        BergItemType.STANDING_ONE_FOOT_IN_FRONT to BergItemDefinition(
            type = BergItemType.STANDING_ONE_FOOT_IN_FRONT,
            titleRes = R.string.berg_standingunsupportedonefootinfront_title,
            descriptionRes = R.string.berg_standingunsupportedonefootinfront_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_standingunsupportedonefootinfront_score_4),
                BergScoreOption(3, R.string.berg_standingunsupportedonefootinfront_score_3),
                BergScoreOption(2, R.string.berg_standingunsupportedonefootinfront_score_2),
                BergScoreOption(1, R.string.berg_standingunsupportedonefootinfront_score_1),
                BergScoreOption(0, R.string.berg_standingunsupportedonefootinfront_score_0),
            ),
            needsTimer = true
        ),
        // Item 14
        BergItemType.STANDING_ON_ONE_LEG to BergItemDefinition(
            type = BergItemType.STANDING_ON_ONE_LEG,
            titleRes = R.string.berg_standingononeleg_title,
            descriptionRes = R.string.berg_standingononeleg_description,
            scoringOptions = listOf(
                BergScoreOption(4, R.string.berg_standingononeleg_score_0),
                BergScoreOption(3, R.string.berg_standingononeleg_score_1),
                BergScoreOption(2, R.string.berg_standingononeleg_score_2),
                BergScoreOption(1, R.string.berg_standingononeleg_score_3),
                BergScoreOption(0, R.string.berg_standingononeleg_score_4),
            ),
            needsTimer = true
        ),
    )
}