package com.aentrena.escalasrhb.presentation.navigation

import com.aentrena.escalasrhb.domain.model.TestType

object Routes {
    const val HOME = "home"
    const val PATIENTS = "patients"
    const val CONTACT = "contact"
    const val PATIENT_SELECT = "select patient"
    const val SCALE_MENU = "scale_menu/{testType}"

    fun scaleMenu(type: TestType) = "scale_menu/${type.name}"
}