package com.aentrena.escalasrhb.data.services.pdf

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.graphics.Canvas
import androidx.core.content.FileProvider
import com.aentrena.escalasrhb.data.services.pdf.strategies.BergPdfStrategy
import com.aentrena.escalasrhb.data.services.pdf.strategies.MotricityIndexPdfStrategy
import com.aentrena.escalasrhb.data.services.pdf.strategies.TestPdfStrategy
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.Patient
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Genera un PDF para el test y paciente dados, lo guarda en caché
 * y devuelve una Uri para compartirlo con otras apps.
 *
 * @param test Test clínico con los datos a exportar
 * @param patient Paciente al que pertenece el test
 * @return Uri que apunta al PDF generado, lista para usar en un Intent de compartir
 */

class PdfGenerator(
    private val context: Context,
    private val layout: PdfLayout = PdfLayout.A4) {

    fun generatePdf(test: ClinicalTest, patient: Patient): Uri {
        //Selecciona estrategia
        val strategy = getStrategy(test)
        // Documento pdf en memoria
        val document = PdfDocument()
        //Stream donde volcaremos los bytes del pdf una vez generado
        val outputStream = ByteArrayOutputStream()

        try {
            var currentPageNumber = 1

            val pageHolder = arrayOf(document.startPage(createPageInfo(currentPageNumber)))

            // Callback que la estrategia invoca cuando no hay espacio
            val requestNewPage: () -> Canvas = {
                document.finishPage(pageHolder[0]) // Cierra pagina
                currentPageNumber++
                pageHolder[0] = document.startPage(createPageInfo(currentPageNumber))
                pageHolder[0].canvas
            }

            strategy.drawContent(test, patient, pageHolder[0].canvas, layout, requestNewPage)

            document.finishPage(pageHolder[0])
            document.writeTo(outputStream)

        } finally {
            //Se ejecuta siempre, libera los recursos nativos del documento del stream
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

    private fun createPageInfo(pageNumber: Int): PdfDocument.PageInfo {
        return PdfDocument.PageInfo.Builder(
            layout.pageWidth.toInt(),
            layout.pageHeight.toInt(),
            pageNumber
        ).create()
    }

    private fun getStrategy(test: ClinicalTest): TestPdfStrategy = when (test.testType) {
        TestType.BERG -> BergPdfStrategy(context = context)
        TestType.MOTRICITY_INDEX -> MotricityIndexPdfStrategy(context)
        TestType.TRUNK_CONTROL_TEST -> BergPdfStrategy(context)
    }
}