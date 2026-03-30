package com.aentrena.escalasrhb.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aentrena.escalasrhb.data.local.entities.BergTestEntity
import com.aentrena.escalasrhb.data.local.entities.MotricityIndexEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MotricityIndexDao {
    @Query("SELECT * FROM motricity_index WHERE patientId = :patientId ORDER BY date DESC")
    fun getMotricityIndexByPatient(patientId: String): Flow<List<MotricityIndexEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMotricityIndex(test: MotricityIndexEntity)

    @Delete
    suspend fun deleteMotricityIndex(test: MotricityIndexEntity)
}