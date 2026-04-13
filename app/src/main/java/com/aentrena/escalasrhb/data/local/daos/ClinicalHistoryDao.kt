package com.aentrena.escalasrhb.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aentrena.escalasrhb.data.local.entities.ClinicalHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClinicalHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: ClinicalHistoryEntity)

    @Query("SELECT * FROM clinical_history WHERE patientId = :patientId ORDER BY date DESC")
    fun getPatientHistory(patientId: String): Flow<List<ClinicalHistoryEntity>>

    @Query("SELECT * FROM clinical_history WHERE id = :testId")
    fun getById(testId: String): Flow<ClinicalHistoryEntity?>
}