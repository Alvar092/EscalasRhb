package com.aentrena.escalasrhb.presentation.bergTest.resources

import android.content.Context
import androidx.annotation.StringRes
import com.aentrena.escalasrhb.domain.model.scales.BergItemType

data class BergItemDefinition(
    val type: BergItemType,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val scoringOptions: List<BergScoreOption>,
    val needsTimer: Boolean
) {
    fun scoreDescription(context: Context, forScore: Int): String {
        val option = scoringOptions.firstOrNull { it.score == forScore}
        return option?.let { context.getString(it.textRes)} ?: ""
    }
}
