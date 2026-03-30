package com.aentrena.escalasrhb.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aentrena.escalasrhb.data.local.entities.TrunkControlTestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrunkControlTestDao {
    @Query("SELECT * FROM trunk_control WHERE patientId = :patientId ORDER BY date DESC")
    fun getTrunkControlTestByPatient(patientId: String): Flow<List<TrunkControlTestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrunkControlTest(test: TrunkControlTestEntity)

    @Delete
    suspend fun deleteTrunkControlTest(test: TrunkControlTestEntity)
}