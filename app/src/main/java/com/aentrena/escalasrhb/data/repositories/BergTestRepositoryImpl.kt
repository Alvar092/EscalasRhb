package com.aentrena.escalasrhb.data.repositories

import com.aentrena.escalasrhb.data.local.daos.BergTestDao
import com.aentrena.escalasrhb.data.local.daos.ClinicalHistoryDao
import com.aentrena.escalasrhb.data.local.mappers.BergTestMapper.toDomain
import com.aentrena.escalasrhb.data.local.mappers.BergTestMapper.toEntity
import com.aentrena.escalasrhb.data.local.mappers.ClinicalTestMapper.toClinicalHistoryEntity
import com.aentrena.escalasrhb.data.local.mappers.ClinicalTestMapper.toEntity
import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import java.util.UUID


class BergTestRepositoryImpl @Inject constructor(
    private val dao: BergTestDao,
    private val indexDao: ClinicalHistoryDao
) : BergTestRepository {

    override fun getAll(): Flow<List<BergTest>> =
        dao.getAllBergTests().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getByPatient(patientId: UUID): Flow<List<BergTest>> =
        dao.getBergTestsByPatient(patientId.toString()).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getById(id: UUID): Flow<BergTest?> =
        dao.getBergTestById(id.toString()).map { it?.toDomain() }

    override suspend fun save(test: BergTest) {
        dao.insertBergTest(test.toEntity())
        indexDao.insert(test.toClinicalHistoryEntity())
    }
}