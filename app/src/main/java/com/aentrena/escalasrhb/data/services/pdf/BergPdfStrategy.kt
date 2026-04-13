package com.aentrena.escalasrhb.data.services.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemCatalog
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class BergPdfStrategy(private val context: Context) : TestPdfStrategy {

    override fun drawContent(
        test: ClinicalTest,
        patient: Patient,
        canvas: Canvas,
        layout: PdfLayout
    ): Float {

        val bergTest = test as? BergTest ?: return layout.margin
        var currentY = layout.margin
        var currentCanvas = canvas
        var currentPage = 1


        currentY = drawHeader(canvas, test.testType.name, currentY, layout)
        currentY += 15f

        currentY = drawSection(canvas, "Datos del Paciente", currentY, layout)
        currentY = drawPatientData(canvas, patient, currentY, layout)
        currentY += 20f

        currentY = drawSection(canvas, "Información de la Evaluación", currentY, layout)
        currentY = drawText(canvas, "Fecha: ${formatDate(test.date)}", currentY, layout)
        currentY += 20f

        currentY = drawTotalScore(canvas, test.totalScore, test.maxScore ?: 56, currentY, layout)
        currentY += 30f

        val itemsPdf = prepareItemsForPdf(bergTest)
        currentY = drawSection(canvas, "Detalle de Ítems", currentY, layout)
        currentY += 10f


        /* for (item in itemsPdf) {
            // Nueva página si no hay espacio
            if (currentY > layout.pageHeight - 150f) {
                drawPageNumber(canvas, currentPage, layout)
                document.finishPage(page)
                currentPage++
                val newPageInfo = PdfDocument.PageInfo.Builder(
                    layout.pageWidth.toInt(), layout.pageHeight.toInt(), currentPage
                ).create()
                val newPage = document.startPage(newPageInfo)
                // Nota: necesitas pasar el nuevo canvas — ver nota abajo
                currentY = layout.margin
            }

            currentY = drawItem(canvas, item, currentY, layout)
            currentY += 8f
        } */

        drawPageNumber(canvas, currentPage, layout)
        return currentY
    }

    // MARK: - Prepare items

    private fun prepareItemsForPdf(bergTest: BergTest): List<BergItemPdf> {
        val definitions = BergItemCatalog.definitions
        return bergTest.items.mapIndexedNotNull { index, item ->
            val definition = definitions[item.itemType] ?: return@mapIndexedNotNull  null
            val scoreDescription = item.score?.let { score: Int ->
                definition.scoringOptions.firstOrNull { it.score == score }?.let { option ->
                    context.getString(option.textRes)
                }
            } ?: ""
            BergItemPdf(index + 1, definition, item, bergTest, scoreDescription)
        }
    }

    // MARK: - Drawing

    private fun drawHeader(canvas: Canvas, testName: String, y: Float, layout: PdfLayout): Float {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 26f
            isFakeBoldText = true
            color = Color.rgb(51, 76, 127)
        }
        val textWidth = paint.measureText(testName)
        canvas.drawText(testName, (layout.pageWidth - textWidth) / 2, y + 26f, paint)

        val lineY = y + 36f
        val linePaint = Paint().apply {
            color = Color.rgb(51, 76, 127)
            strokeWidth = 2f
        }
        canvas.drawLine(layout.margin, lineY, layout.pageWidth - layout.margin, lineY, linePaint)
        return lineY + 5f
    }

    private fun drawSection(canvas: Canvas, title: String, y: Float, layout: PdfLayout): Float {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 16f
            isFakeBoldText = true
            color = Color.DKGRAY
        }
        canvas.drawText(title, layout.margin, y + 16f, paint)

        val linePaint = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 1f
        }
        canvas.drawLine(layout.margin, y + 23f, layout.pageWidth - layout.margin, y + 23f, linePaint)
        return y + 35f
    }

    private fun drawPatientData(canvas: Canvas, patient: Patient, y: Float, layout: PdfLayout): Float {
        var currentY = y
        currentY = drawText(canvas, "Nombre: ${patient.name}", currentY, layout)
        val age = calculateAge(patient.dateOfBirth)
        currentY = drawText(canvas, "Edad: $age años", currentY, layout)
        return currentY
    }

    private fun drawText(canvas: Canvas, text: String, y: Float, layout: PdfLayout): Float {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 12f
            color = Color.BLACK
        }
        canvas.drawText(text, layout.margin, y + 12f, paint)
        return y + 22f
    }

    private fun drawTotalScore(canvas: Canvas, score: Int, maxScore: Int, y: Float, layout: PdfLayout): Float {
        val boxHeight = 80f
        val rect = RectF(layout.margin, y, layout.margin + layout.contentWidth, y + boxHeight)

        val percentage = score.toDouble() / maxScore
        val bgColor = when {
            percentage >= 0.8 -> Color.rgb(229, 250, 229)
            percentage >= 0.5 -> Color.rgb(250, 242, 229)
            else -> Color.rgb(250, 229, 229)
        }

        val fillPaint = Paint().apply { color = bgColor; style = Paint.Style.FILL }
        canvas.drawRoundRect(rect, 12f, 12f, fillPaint)

        val borderPaint = Paint().apply {
            color = Color.LTGRAY; style = Paint.Style.STROKE; strokeWidth = 1f
        }
        canvas.drawRoundRect(rect, 12f, 12f, borderPaint)

        val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 14f; color = Color.DKGRAY }
        val label = "Puntuación Total"
        canvas.drawText(label, rect.centerX() - labelPaint.measureText(label) / 2, y + 25f, labelPaint)

        val scorePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 36f; isFakeBoldText = true; color = Color.rgb(51, 76, 127)
        }
        val scoreText = "$score / $maxScore"
        canvas.drawText(scoreText, rect.centerX() - scorePaint.measureText(scoreText) / 2, y + 65f, scorePaint)

        return y + boxHeight
    }

    private fun drawItem(canvas: Canvas, item: BergItemPdf, y: Float, layout: PdfLayout): Float {
        val itemHeight = 120f

        if (item.number % 2 == 0) {
            val bgPaint = Paint().apply { color = Color.rgb(247, 247, 247); style = Paint.Style.FILL }
            canvas.drawRect(layout.margin, y, layout.margin + layout.contentWidth, y + itemHeight, bgPaint)
        }

        val numberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 12f; isFakeBoldText = true; color = Color.rgb(76, 102, 153)
        }
        canvas.drawText("${item.number}.", layout.margin + 10f, y + 22f, numberPaint)

        val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 11f; color = Color.BLACK }
        canvas.drawText(item.title, layout.margin + 45f, y + 22f, titlePaint)

        var optionY = y + 38f
        for (option in item.scoringOptions) {
            val isSelected = option.score == item.score
            val optionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                textSize = 11f
                isFakeBoldText = isSelected
                color = if (isSelected) Color.BLACK else Color.DKGRAY
            }
            canvas.drawText(option.textRes.toString(), layout.margin + 45f, optionY + 11f, optionPaint)
            optionY += 14f
        }

        return y + itemHeight
    }

    private fun drawPageNumber(canvas: Canvas, number: Int, layout: PdfLayout) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 9f; color = Color.GRAY }
        val text = "Página $number"
        canvas.drawText(
            text,
            layout.pageWidth - layout.margin - paint.measureText(text),
            layout.pageHeight - layout.margin + 10f,
            paint
        )
    }

    // MARK: - Helpers

    private fun formatDate(timeStamp: Long): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES")).format(Date(timeStamp))
    }

    private fun calculateAge(birthTimestamp: Long): Int {
        val birth = Instant.ofEpochMilli(birthTimestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return Period.between(birth, LocalDate.now()).years
    }
}