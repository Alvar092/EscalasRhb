package com.aentrena.escalasrhb.data.repositories

import com.aentrena.escalasrhb.data.local.daos.ClinicalHistoryDao
import com.aentrena.escalasrhb.data.local.entities.ClinicalHistoryEntity
import com.aentrena.escalasrhb.data.local.mappers.ClinicalTestMapper.toClinicalHistoryDomain
import com.aentrena.escalasrhb.data.local.mappers.ClinicalTestMapper.toClinicalHistoryEntity
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.repositories.ClinicalHistoryRepository
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import kotlin.uuid.toKotlinUuid

class ClinicalHistoryRepositoryImpl @Inject constructor(
    private val dao: ClinicalHistoryDao
): ClinicalHistoryRepository {
    override fun getPatientHistory(patientId: UUID): Flow<List<ClinicalHistory>> =
        dao.getPatientHistory(patientId = patientId.toString()).map { entities ->
            entities.map {it.toClinicalHistoryDomain()}
        }

    override suspend fun addEntry(test: ClinicalTest) =
        dao.insert(test.toClinicalHistoryEntity())

    override fun getById(testId: UUID): Flow<ClinicalHistory?> =
        dao.getById(testId = testId.toString())
            .map {it?.toClinicalHistoryDomain()}
}