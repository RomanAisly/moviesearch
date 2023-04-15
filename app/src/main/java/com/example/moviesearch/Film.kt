package com.example.moviesearch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Film(
    val title: String,
    val poster: Int,
    val description: String
) :Parcelable
