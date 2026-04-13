package com.aentrena.escalasrhb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aentrena.escalasrhb.data.local.daos.BergTestDao
import com.aentrena.escalasrhb.data.local.daos.ClinicalHistoryDao
import com.aentrena.escalasrhb.data.local.daos.MotricityIndexDao
import com.aentrena.escalasrhb.data.local.daos.PatientDao
import com.aentrena.escalasrhb.data.local.daos.TrunkControlTestDao
import com.aentrena.escalasrhb.data.local.entities.BergTestEntity
import com.aentrena.escalasrhb.data.local.entities.ClinicalHistoryEntity
import com.aentrena.escalasrhb.data.local.entities.MotricityIndexEntity
import com.aentrena.escalasrhb.data.local.entities.PatientEntity
import com.aentrena.escalasrhb.data.local.entities.TrunkControlTestEntity

@Database(
    entities = [
        PatientEntity::class,
        ClinicalHistoryEntity::class,
        BergTestEntity::class,
        MotricityIndexEntity::class,
        TrunkControlTestEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun patientDao(): PatientDao

    abstract fun clinicalHistoryDao(): ClinicalHistoryDao
    abstract fun bergTestDao(): BergTestDao
    abstract fun motricityIndexDao(): MotricityIndexDao
    abstract fun trunkControlTestDao(): TrunkControlTestDao
}