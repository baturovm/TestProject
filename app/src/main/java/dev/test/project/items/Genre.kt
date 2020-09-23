package dev.test.project.items

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre (
    var genre: String,
    var id: Int
): Parcelable