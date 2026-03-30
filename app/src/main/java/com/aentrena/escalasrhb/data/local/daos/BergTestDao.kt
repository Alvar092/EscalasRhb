package com.aentrena.escalasrhb.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aentrena.escalasrhb.data.local.entities.BergTestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BergTestDao {
    @Query("SELECT * FROM berg_tests WHERE patientId = :patientId ORDER BY date DESC")
    fun getBergTestsByPatient(patientId: String): Flow<List<BergTestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBergTest(test: BergTestEntity)

    @Delete
    suspend fun deleteBergTest(test: BergTestEntity)
}