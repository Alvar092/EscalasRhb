package com.aentrena.escalasrhb.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "berg_tests",
    foreignKeys = [ForeignKey(
        entity = PatientEntity::class,
        parentColumns = ["id"],
        childColumns = ["patientId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["patientId"])]
)

data class BergTestEntity(
    @PrimaryKey
    val id: String,
    val date: Long,
    val evaluator: String?,
    val patientId: String,
    val itemsJson: String,
    val maxScore: Int = 56
)