package com.aentrena.escalasrhb.domain.interfaces

import java.util.UUID

interface ClinicalTest {
    val id: UUID
    val date: Long
    val evaluator: String?
    val patientId: UUID
    val maxScore: Int?
    val totalScore: Int
    val items: List<ClinicalTestItem>
}

interface ClinicalTestItem {
    val id: UUID
    val score: Int?
}