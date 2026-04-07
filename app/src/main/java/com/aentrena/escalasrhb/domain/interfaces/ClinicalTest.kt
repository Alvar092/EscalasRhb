package com.aentrena.escalasrhb.domain.interfaces

import com.aentrena.escalasrhb.domain.model.TestType
import java.util.UUID

interface ClinicalTest {
    val id: UUID
    val date: Long
    val evaluator: String?
    val patientId: UUID
    val maxScore: Int?
    val totalScore: Int
    val items: List<ClinicalTestItem>

    val testType: TestType
}

interface ClinicalTestItem {
    val id: UUID
    val score: Int?
}