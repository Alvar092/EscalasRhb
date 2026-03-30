package com.aentrena.escalasrhb.data.local.mappers

import com.aentrena.escalasrhb.data.local.entities.PatientEntity
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.google.gson.Gson
import java.util.UUID

object PatientMapper {
    private val gson = Gson()

    fun Patient.toEntity(): PatientEntity = PatientEntity(
        id = this.id.toString(),
        name = this.name,
        dateOfBirth = this.dateOfBirth
    )

    fun PatientEntity.toDomain(): Patient = Patient(
        id = UUID.fromString(this.id),
        name = this.name,
        dateOfBirth = this.dateOfBirth
    )
}