package com.aentrena.escalasrhb.data.services.pdf

data class PdfLayout(
    val pageWidth: Float,
    val pageHeight: Float,
    val margin: Float
) {
    val contentWidth: Float get() = pageWidth - (margin * 2)
    val contentHeight: Float get() = pageHeight - (margin * 2)

    companion object {
        val A4 = PdfLayout(pageWidth = 595f, pageHeight = 842f, margin = 50f)
    }
}
