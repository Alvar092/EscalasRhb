package com.aentrena.escalasrhb.data.local.mappers

import com.aentrena.escalasrhb.data.local.entities.TrunkControlTestEntity
import com.aentrena.escalasrhb.domain.model.scales.BodySide
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTestItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

object TrunkControlMapper {
    private val gson = Gson()

    fun TrunkControlTest.toEntity(): TrunkControlTestEntity = TrunkControlTestEntity(
        id = this.id.toString(),
        date = this.date,
        evaluator = this.evaluator ?: "N/A",
        patientId = this.patientId.toString(),
        side = this.side ?: BodySide.RIGHT,
        itemsJson = gson.toJson(this.items),
        maxScore = this.maxScore
    )

    fun TrunkControlTestEntity.toDomain(): TrunkControlTest = TrunkControlTest(
        id = UUID.fromString(this.id),
        date = this.date,
        evaluator = this.evaluator,
        patientId = UUID.fromString(this.patientId),
        items = gson.fromJson(
            this.itemsJson,
            object : TypeToken<List<TrunkControlTestItem>>() {}.type
        ),
        maxScore = this.maxScore
    )
}