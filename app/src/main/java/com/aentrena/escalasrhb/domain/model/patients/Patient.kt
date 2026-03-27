package com.aentrena.escalasrhb.domain.model.patients

import java.util.Calendar
import java.util.UUID

data class Patient(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val dateOfBirth: Long
) {
    val age: Int
        get()  {
            val dob = Calendar.getInstance().apply { timeInMillis = dateOfBirth }
            val today = Calendar.getInstance()
            var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) age--
            return age
        }
}