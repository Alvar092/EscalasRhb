package com.aentrena.escalasrhb.data.services.pdf

import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.patients.Patient

interface TestPdfStrategy {
    fun drawContent(
        test: ClinicalTest,
        patient: Patient,
        canvas: Canvas,
        page: PdfDocument.Page,
        document: PdfDocument,
        layout: PdfLayout
    ): Float
}