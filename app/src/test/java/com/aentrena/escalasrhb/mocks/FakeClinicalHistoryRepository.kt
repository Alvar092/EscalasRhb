package com.aentrena.escalasrhb.mocks

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.repositories.ClinicalHistoryRepository
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID

class FakeClinicalHistoryRepository : ClinicalHistoryRepository {

    private val history = MutableStateFlow<List<ClinicalHistory>>(emptyList())

    fun add(entry: ClinicalHistory) {
        history.value = history.value + entry
    }

    override fun getPatientHistory(patientId: UUID): Flow<List<ClinicalHistory>> =
        history.map { list -> list.filter { it.patientId == patientId.toString() } }

    override suspend fun addEntry(test: ClinicalTest) {
        add(
            ClinicalHistory(
                id = test.id.toString(),
                patientId = test.patientId.toString(),
                evaluator = test.evaluator,
                testType = test.testType,
                date = test.date,
                totalScore = test.totalScore,
                maxScore = test.maxScore ?: 0
            )
        )
    }

    override fun getById(testId: UUID): Flow<ClinicalHistory?> =
        history.map { list -> list.firstOrNull { it.id == testId.toString() } }
}
