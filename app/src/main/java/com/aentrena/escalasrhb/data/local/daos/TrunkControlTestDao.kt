package com.aentrena.escalasrhb.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aentrena.escalasrhb.data.local.entities.TrunkControlTestEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TrunkControlTestDao {
    @Query("SELECT * FROM trunk_control ORDER BY date ASC")
    fun getAllTrunkControlTests(): Flow<List<TrunkControlTestEntity>>

    @Query("SELECT * FROM trunk_control WHERE patientId = :patientId ORDER BY date DESC")
    fun getTrunkControlTestByPatient(patientId: String): Flow<List<TrunkControlTestEntity>>

    @Query("SELECT * FROM trunk_control WHERE id = id ORDER BY date DESC")
    fun getTrunkControlTestById(id: String): Flow<TrunkControlTestEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrunkControlTest(test: TrunkControlTestEntity)

    @Delete
    suspend fun deleteTrunkControlTest(test: TrunkControlTestEntity)
}