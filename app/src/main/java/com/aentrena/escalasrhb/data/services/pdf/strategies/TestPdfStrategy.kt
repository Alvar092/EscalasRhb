package com.aentrena.escalasrhb.data.services.pdf.strategies

import android.graphics.Canvas
import com.aentrena.escalasrhb.data.services.pdf.PdfLayout
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.patients.Patient

interface TestPdfStrategy {
    fun drawContent(
        test: ClinicalTest,
        patient: Patient,
        canvas: Canvas,
        layout: PdfLayout,
        requestNewPage: () -> Canvas
    ): Float
}