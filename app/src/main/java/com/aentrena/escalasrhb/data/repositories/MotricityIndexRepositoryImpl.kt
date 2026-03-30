package com.aentrena.escalasrhb.data.repositories

import com.aentrena.escalasrhb.data.local.daos.MotricityIndexDao
import com.aentrena.escalasrhb.data.local.mappers.MotricityIndexMapper.toDomain
import com.aentrena.escalasrhb.data.local.mappers.MotricityIndexMapper.toEntity
import com.aentrena.escalasrhb.domain.interfaces.repositories.MotricityIndexRepository
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import java.util.UUID


class MotricityIndexRepositoryImpl @Inject constructor(
    private val dao: MotricityIndexDao
) : MotricityIndexRepository {

    override fun getAll(): Flow<List<MotricityIndexTest>> =
        dao.getAllMotricityIndex().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getByPatient(patientId: UUID): Flow<List<MotricityIndexTest>> =
        dao.getMotricityIndexByPatient(patientId.toString()).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getById(id: UUID): Flow<MotricityIndexTest?> =
        dao.getMotricityIndexById(id.toString()).map{it?.toDomain()}


    override suspend fun save(test: MotricityIndexTest) =
        dao.insertMotricityIndex(test.toEntity())


    override suspend fun update(test: MotricityIndexTest) =
        dao.insertMotricityIndex(test.toEntity()) // REPLACE hace upsert

}