package com.aentrena.escalasrhb.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aentrena.escalasrhb.domain.model.scales.BodySide

@Entity(tableName = "trunk_control")
data class TrunkControlTestEntity(
    @PrimaryKey
    val id: String,
    val date: Long,
    val evaluator: String?,
    val patientId: String,
    val side: BodySide,
    val itemsJson: String,
    val maxScore: Int? = 100
)