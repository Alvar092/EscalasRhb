package com.aentrena.escalasrhb.data.local.mappers

import com.aentrena.escalasrhb.data.local.entities.MotricityIndexEntity
import com.aentrena.escalasrhb.domain.model.scales.BodySide
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItem
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

object MotricityIndexMapper {
    private val gson = Gson()

    fun MotricityIndexTest.toEntity(): MotricityIndexEntity = MotricityIndexEntity(
        id = this.id.toString(),
        date = this.date,
        evaluator = this.evaluator ?: "N/A",
        patientId = this.patientId.toString(),
        side = this.side ?: BodySide.RIGHT,
        itemsJson = gson.toJson(this.items),
        maxScore = this.maxScore
    )

    fun MotricityIndexEntity.toDomain(): MotricityIndexTest = MotricityIndexTest(
        id = UUID.fromString(this.id),
        date = this.date,
        evaluator = this.evaluator,
        patientId = UUID.fromString(this.patientId),
        items = gson.fromJson(
            this.itemsJson,
            object : TypeToken<List<MotricityIndexItem>>() {}.type
        ),
        maxScore = this.maxScore
    )
}