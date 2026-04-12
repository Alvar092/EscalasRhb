package com.aentrena.escalasrhb.domain.model.patients

import com.aentrena.escalasrhb.domain.model.TestType
import java.time.LocalDate

class ClinicalHistory (
    val id: String,
    val patientId: String,
    val evaluator: String? = null,
    val testType: TestType,
    val date: Long,
    val totalScore: Int,
    val maxScore: Int
)