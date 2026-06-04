package com.aentrena.escalasrhb.analytics

import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsHelper {
    private val crashlytics get() = FirebaseCrashlytics.getInstance()

    fun setScreen(screen: String) = crashlytics.setCustomKey("screen", screen)
    fun setTestType(type: String) = crashlytics.setCustomKey("test_type", type)
    fun setTestId(id: String) = crashlytics.setCustomKey("test_id", id)
    fun setPatientId(id: String) = crashlytics.setCustomKey("patient_id", id)
    fun setPatientSelected(selected: Boolean) = crashlytics.setCustomKey("patient_selected", selected)
    fun setItemIndex(index: Int) = crashlytics.setCustomKey("item_index", index)
    fun setCurrentScore(score: Int) = crashlytics.setCustomKey("current_score", score)
    fun setSide(side: String) = crashlytics.setCustomKey("side", side)
}
