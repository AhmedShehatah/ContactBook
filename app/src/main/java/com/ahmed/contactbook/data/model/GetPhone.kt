package com.ahmed.contactbook.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetPhone(
    val id: Int,
    val type_id: Int,
    val value: String
) : Parcelable