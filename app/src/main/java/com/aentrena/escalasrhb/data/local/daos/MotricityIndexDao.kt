package com.aentrena.escalasrhb.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aentrena.escalasrhb.data.local.entities.MotricityIndexEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface MotricityIndexDao {
    @Query("SELECT * FROM motricity_index ORDER BY date ASC")
    fun getAllMotricityIndex(): Flow<List<MotricityIndexEntity>>

    @Query("SELECT * FROM motricity_index WHERE patientId = :patientId ORDER BY date DESC")
    fun getMotricityIndexByPatient(patientId: String): Flow<List<MotricityIndexEntity>>

    @Query("SELECT * FROM motricity_index WHERE id = id ORDER BY date DESC")
    fun getMotricityIndexById(id: String): Flow<MotricityIndexEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMotricityIndex(test: MotricityIndexEntity)

    @Delete
    suspend fun deleteMotricityIndex(test: MotricityIndexEntity)
}