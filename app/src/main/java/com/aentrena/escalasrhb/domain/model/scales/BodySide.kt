package com.aentrena.escalasrhb.domain.model.scales

import com.aentrena.escalasrhb.R

enum class BodySide {
    RIGHT, LEFT;

    val displayNameRes: Int
        get() = when (this) {
            RIGHT -> R.string.body_side_right
            LEFT -> R.string.body_side_left
        }
}