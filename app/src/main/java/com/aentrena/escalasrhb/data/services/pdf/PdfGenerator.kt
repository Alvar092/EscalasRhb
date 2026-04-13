package com.aentrena.escalasrhb.data.services.pdf

import android.graphics.pdf.PdfDocument
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.Patient
import java.io.ByteArrayOutputStream


class PdfGenerator(private val layout: PdfLayout = PdfLayout.A4) {

    fun generatePdf(test: ClinicalTest, patient: Patient): ByteArray {
        val strategy = getStrategy(test)
        val document = PdfDocument()

        val pageInfo = PdfDocument.PageInfo.Builder(
            layout.pageWidth.toInt(),
            layout.pageHeight.toInt(),
            1
        ).create()

        val page = document.startPage(pageInfo)
        strategy.drawContent(test, patient, page.canvas, page, document, layout)
        document.finishPage(page)

        val outputStream = ByteArrayOutputStream()
        document.writeTo(outputStream)
        document.close()
        return outputStream.toByteArray()
    }

    private fun getStrategy(test: ClinicalTest): TestPdfStrategy = when (test.testType) {
        TestType.BERG -> BergPdfStrategy()
        TestType.MOTRICITY_INDEX -> MotricityIndexPdfStrategy()
        TestType.TRUNK_CONTROL -> TrunkControlPdfStrategy()
    }
}