package com.aentrena.escalasrhb.domain.model

import androidx.compose.ui.res.stringResource
import com.aentrena.escalasrhb.R

enum class TestType { BERG, MOTRICITY_INDEX, TRUNK_CONTROL_TEST }

val TestType.displayName: String
    get() = when (this) {
        TestType.BERG -> "Berg Balance Scale"
        TestType.MOTRICITY_INDEX -> "Motricity Index"
        TestType.TRUNK_CONTROL_TEST -> "Trunk Control Test"
    }

class ClinicalTestInfo(
    val testType: TestType,
    val descriptionResId: Int,
    val materialsResId: Int,
    val scoringResId: Int,
    val interpretationResId: Int,
    val recommendationsResId: Int,
    val referenceUrl: String
) {
    companion object {
            val berg = ClinicalTestInfo(
                testType = TestType.BERG,
                descriptionResId = R.string.berg_description,
                materialsResId = R.array.berg_materials,
                scoringResId = R.string.berg_scoring,
                interpretationResId = R.string.berg_interpretation,
                recommendationsResId = R.string.berg_recommendations,
                referenceUrl = "https://www.sralab.org/rehabilitation-measures/berg-balance-scale"
            )

            val motricityIndex = ClinicalTestInfo(
                testType = TestType.MOTRICITY_INDEX,
                descriptionResId = R.string.motricity_description,
                materialsResId = R.array.motricity_materials,
                scoringResId = R.string.motricity_scoring,
                interpretationResId = R.string.motricity_interpretation,
                recommendationsResId = R.string.motricity_recommendations,
                referenceUrl = "https://www.sralab.org/rehabilitation-measures/motricity-index"
            )

            val trunkControlTest = ClinicalTestInfo(
                testType = TestType.TRUNK_CONTROL_TEST,
                descriptionResId = R.string.tct_description,
                materialsResId = R.array.tct_materials,
                scoringResId = R.string.tct_scoring,
                interpretationResId = R.string.tct_interpretation,
                recommendationsResId = R.string.tct_recommendations,
                referenceUrl = "https://www.sralab.org/rehabilitation-measures/trunk-control-test"
            )
    }
}

val TestType.clinicalInfo: ClinicalTestInfo
    get() = when (this) {
        TestType.BERG               -> ClinicalTestInfo.berg
        TestType.MOTRICITY_INDEX    -> ClinicalTestInfo.motricityIndex
        TestType.TRUNK_CONTROL_TEST -> ClinicalTestInfo.trunkControlTest
    }