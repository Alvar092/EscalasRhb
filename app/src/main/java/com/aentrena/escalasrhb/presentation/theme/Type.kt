package com.aentrena.escalasrhb.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val AppTypography = Typography(

    // Regular
    displayLarge = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal
    ),

    displayMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal
    ),

    bodyLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),

    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),

    // SemiBold
    headlineLarge = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.SemiBold
    ),

    headlineMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold
    ),

    titleLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
)