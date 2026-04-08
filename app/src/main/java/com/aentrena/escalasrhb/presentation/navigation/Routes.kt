package com.aentrena.escalasrhb.presentation.navigation

import com.aentrena.escalasrhb.domain.model.TestType

object Routes {
    const val HOME = "home"
    const val PATIENTS = "patients"
    const val CONTACT = "contact"
    const val SCALE_MENU = "scale_menu/{testType}"
    const val SCALE_INFO = "scale_info/{testType}"

    fun scaleMenu(type: TestType) = "scale_menu/${type.name}"
    fun scaleInfo(type: TestType) = "scale_info/${type.name}"
}