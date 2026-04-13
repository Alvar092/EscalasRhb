package com.aentrena.escalasrhb.data.services.pdf

import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemDefinition
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergScoreOption

data class BergItemPdf(
    val number: Int,
    val title: String,
    val description: String,
    val scoringOptions: List<BergScoreOption>,
    val score: Int,
    val scoreDescription: String,
    val maxScore: Int
) {
    constructor(
        number: Int,
        definition: BergItemDefinition,
        item: BergItem,
        test: BergTest,
        scoreDescription: String
    ) : this(
        number = number,
        title = definition.titleRes.toString(),
        description = definition.descriptionRes.toString(),
        scoringOptions = definition.scoringOptions,
        score = item.score ?: 0,
        scoreDescription = scoreDescription,
        maxScore = test.maxScore ?: 56
    )
}