package com.aentrena.escalasrhb.presentation.navigation

import com.aentrena.escalasrhb.domain.model.TestType
import java.util.UUID

object Routes {
    const val HOME = "home"
    const val PATIENTS = "patients"
    const val CONTACT = "contact"
    const val SCALE_MENU = "scale_menu/{testType}"
    const val SCALE_INFO = "scale_info/{testType}"
    const val TEST = "test/{testType}/{testId}"
    const val TEST_RESULT = "test/{testId}"

    fun scaleMenu(type: TestType) = "scale_menu/${type.name}"
    fun scaleInfo(type: TestType) = "scale_info/${type.name}"
    fun test(type: TestType, testId: UUID) = "test/${type.name}/$testId"
    fun testResult(testId: UUID) = "test/$testId"
}