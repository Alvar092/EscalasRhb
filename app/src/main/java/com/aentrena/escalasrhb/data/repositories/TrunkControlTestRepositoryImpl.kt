package com.aentrena.escalasrhb.data.repositories

import com.aentrena.escalasrhb.data.local.daos.TrunkControlTestDao
import com.aentrena.escalasrhb.data.local.mappers.TrunkControlMapper.toDomain
import com.aentrena.escalasrhb.data.local.mappers.TrunkControlMapper.toEntity
import com.aentrena.escalasrhb.domain.interfaces.repositories.TrunkControlRepository
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TrunkControlTestRepositoryImpl @Inject constructor(
    private val dao: TrunkControlTestDao
): TrunkControlRepository {
    override fun getAll(): Flow<List<TrunkControlTest>> =
        dao.getAllTrunkControlTests().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getByPatient(patientId: UUID): Flow<List<TrunkControlTest>> =
        dao.getTrunkControlTestByPatient(patientId.toString()).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getById(id: UUID): Flow<TrunkControlTest?> =
        dao.getTrunkControlTestById(id.toString()).map{it?.toDomain()}


    override suspend fun save(test: TrunkControlTest) =
        dao.insertTrunkControlTest(test.toEntity())


    override suspend fun update(test: TrunkControlTest) =
        dao.insertTrunkControlTest(test.toEntity()) // REPLACE hace upsert

}