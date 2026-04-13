package com.aentrena.escalasrhb.data.services.pdf

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.Patient
import java.io.ByteArrayOutputStream
import java.io.File


class PdfGenerator(
    private val context: Context,
    private val layout: PdfLayout = PdfLayout.A4) {

    fun generatePdf(test: ClinicalTest, patient: Patient): Uri {
        val strategy = getStrategy(test)
        val document = PdfDocument()
        val outputStream = ByteArrayOutputStream()

        try {
            val pageInfo = PdfDocument.PageInfo.Builder(
                layout.pageWidth.toInt(),
                layout.pageHeight.toInt(),
                1
            ).create()

            val page = document.startPage(pageInfo)
            strategy.drawContent(test, patient, page.canvas, layout)
            document.finishPage(page)
            document.writeTo(outputStream)
        } finally {
            document.close()
            outputStream.close()
        }

        // Guardar en caché y devolver Uri
        val file = File(context.cacheDir, "evaluacion_${test.testType.name.lowercase()}.pdf")
        file.writeBytes(outputStream.toByteArray())

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    private fun getStrategy(test: ClinicalTest): TestPdfStrategy = when (test.testType) {
        TestType.BERG -> BergPdfStrategy(context = context)
        TestType.MOTRICITY_INDEX -> BergPdfStrategy(context)
        TestType.TRUNK_CONTROL_TEST -> BergPdfStrategy(context)
    }
}