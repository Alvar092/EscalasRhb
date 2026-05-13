package com.aentrena.escalasrhb.presentation.trunkControlTest.resources

import android.content.Context
import androidx.annotation.StringRes
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItemType
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlItemType
import com.aentrena.escalasrhb.presentation.motricityIndex.resources.MotricityScoreOption

class TrunkControlItemDefinition(
    val type: TrunkControlItemType,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val scoringOptions: List<TrunkControlScoreOption>,
    val needsTimer: Boolean
) {
    fun scoreDescription(context: Context, forScore: Int): String {
        val option = scoringOptions.firstOrNull { it.score == forScore }
        return option?.let { context.getString(it.textRes) } ?: ""
    }
}