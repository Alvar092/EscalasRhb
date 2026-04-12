package com.aentrena.escalasrhb.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aentrena.escalasrhb.domain.model.TestType
import java.time.LocalDate


@Entity(tableName = "clinical_history")
data class ClinicalHistoryEntity(
    @PrimaryKey val id: String,
    val patientId: String,
    val evaluator: String?,
    val testType: TestType,
    val date: Long,
    val totalScore: Int,
    val maxScore: Int
)