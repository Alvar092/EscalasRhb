package com.aentrena.escalasrhb.presentation.bergTest.resources

import androidx.annotation.StringRes

data class BergScoreOption(
    val score: Int,
    @StringRes val textRes: Int
)