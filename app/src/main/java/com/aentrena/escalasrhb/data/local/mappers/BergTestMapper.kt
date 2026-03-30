package com.aentrena.escalasrhb.data.local.mappers

import com.aentrena.escalasrhb.data.local.entities.BergTestEntity
import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID


object BergTestMapper {

    private val gson = Gson()

    fun BergTest.toEntity(): BergTestEntity = BergTestEntity(
        id = this.id.toString(),
        date = this.date,
        evaluator = this.evaluator,
        patientId = this.patientId.toString(),
        itemsJson = gson.toJson(this.items),
        maxScore = this.maxScore ?: 56
    )

    fun BergTestEntity.toDomain(): BergTest = BergTest(
        id = UUID.fromString(this.id),
        date = this.date,
        evaluator = this.evaluator,
        patientId = UUID.fromString(this.patientId),
        items = gson.fromJson(
            this.itemsJson,
            object: TypeToken<List<BergItem>>() {}.type
        ),
        maxScore = this.maxScore
    )
}