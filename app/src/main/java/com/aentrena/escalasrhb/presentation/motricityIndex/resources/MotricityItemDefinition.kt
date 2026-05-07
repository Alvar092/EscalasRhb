package com.aentrena.escalasrhb.presentation.motricityIndex.resources

import android.content.Context
import androidx.annotation.StringRes
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItemType


class MotricityItemDefinition(
    val type: MotricityIndexItemType,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val scoringOptions: List<MotricityScoreOption>,
    val needsTimer: Boolean
) {
    fun scoreDescription(context: Context, forScore: Int): String {
        val option = scoringOptions.firstOrNull { it.score == forScore}
        return option?.let { context.getString(it.textRes)} ?: ""
    }
}