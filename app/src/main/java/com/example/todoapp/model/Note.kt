package com.example.todoapp.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note (
    val title: String,
    val titleDescription: String,
    val time: String,
    val color: Int
): Parcelable