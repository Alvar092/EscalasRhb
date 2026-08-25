package com.aentrena.escalasrhb.data.services.pdf.items

import android.content.Context
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItem
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import com.aentrena.escalasrhb.presentation.motricityIndex.resources.MotricityItemDefinition
import com.aentrena.escalasrhb.presentation.motricityIndex.resources.MotricityScoreOption

data class MotricityItemPdf(
    val number: Int,
    val title: String,
    val description: String,
    val scoringOptions: List<MotricityScoreOption>,
    val score: Int,
    val scoreDescription: String,
    val maxScore: Int,
    val note: String?
){
    constructor(
        number: Int,
        definition: MotricityItemDefinition,
        item: MotricityIndexItem,
        test: MotricityIndexTest,
        scoreDescription: String,
        context: Context
    ) : this(
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