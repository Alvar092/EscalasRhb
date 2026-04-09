package com.aentrena.escalasrhb.presentation.bergTest.resources

import androidx.annotation.StringRes
import com.aentrena.escalasrhb.domain.model.scales.BergItemType

data class BergItemDefinition(
    val type: BergItemType,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val scoringOptions: List<BergScoreOption>,
    val needsTimer: Boolean
) {
    fun scoreDescription(forScore: Int): String {
        return scoringOptions.firstOrNull { it.score == forScore }?.description ?: ""
    }
}
