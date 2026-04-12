package com.aentrena.escalasrhb.data.local.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aentrena.escalasrhb.data.local.entities.ClinicalHistoryEntity
import com.aentrena.escalasrhb.domain.model.TestType
import kotlinx.coroutines.flow.Flow

interface ClinicalHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: ClinicalHistoryEntity)

    @Query("SELECT * FROM clinical_history WHERE patientId = :patientId ORDER BY date DESC")
    fun getPatientHistory(patientId: String): Flow<List<ClinicalHistoryEntity>>

    @Query("SELECT testType FROM clinical_history WHERE id = :testId")
    suspend fun getById(testId: String): Flow<ClinicalHistoryEntity>
}