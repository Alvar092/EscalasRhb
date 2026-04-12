package com.aentrena.escalasrhb.data.local.mappers

import com.aentrena.escalasrhb.data.local.entities.ClinicalHistoryEntity
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory

object ClinicalTestMapper {

    fun ClinicalTest.toClinicalHistoryEntity(): ClinicalHistoryEntity = ClinicalHistoryEntity(
        id = this.id.toString(),
        patientId = this.patientId.toString(),
        evaluator = this.evaluator,
        testType = testType,
        date = date,
        totalScore = totalScore,
        maxScore = this.maxScore ?:0
    )

    fun ClinicalHistoryEntity.toClinicalHistoryDomain(): ClinicalHistory = ClinicalHistory(
        id = id,
        patientId = patientId,
        evaluator = evaluator,
        testType = testType,
        date = date,
        totalScore = totalScore,
        maxScore = maxScore
    )
}