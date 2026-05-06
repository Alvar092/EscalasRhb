package com.aentrena.escalasrhb.presentation.results.resources

import androidx.annotation.StringRes

data class TestResultItem(
    @StringRes val titleRes: Int,
    val options: List<Pair<Int, Int>>,
    val selectedScore: Int?
)
