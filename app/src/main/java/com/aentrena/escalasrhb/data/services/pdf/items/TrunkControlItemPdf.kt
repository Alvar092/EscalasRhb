package com.aentrena.escalasrhb.data.services.pdf.items

import android.content.Context
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTestItem
import com.aentrena.escalasrhb.presentation.trunkControlTest.resources.TrunkControlItemDefinition
import com.aentrena.escalasrhb.presentation.trunkControlTest.resources.TrunkControlScoreOption

class TrunkControlItemPdf(
    val number: Int,
    val title: String,
    val description: String,
    val scoringOptions: List<TrunkControlScoreOption>,
    val score: Int,
    val scoreDescription: String,
    val maxScore: Int,
    val note: String?
) {
    constructor(
        number: Int,
        definition: TrunkControlItemDefinition,
        item: TrunkControlTestItem,
        test: TrunkControlTest,
        scoreDescription: String,
        context: Context
    ): this (
        number = number,
        title = context.getString(definition.titleRes),
        description = definition.descriptionRes.toString(),
        scoringOptions = definition.scoringOptions,
        score = item.score ?: 0,
        scoreDescription = scoreDescription,
        maxScore = test.maxScore ?: 100,
        note = item.note
    )
}