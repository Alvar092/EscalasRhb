package com.aentrena.escalasrhb.domain.model.patients

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import com.aentrena.escalasrhb.domain.model.scales.TrunckControlTest

data class PatientHistory(
    val patient: Patient,
    val bergTests: List<BergTest> = emptyList(),
    val motricityIndexTests: List<MotricityIndexTest> = emptyList(),
    val trunckControlTests: List<TrunckControlTest> = emptyList()
) {
    val allTests: List<ClinicalTest>
        get() = (bergTests + motricityIndexTests + trunckControlTests)
            .sortedByDescending { it.date }
}