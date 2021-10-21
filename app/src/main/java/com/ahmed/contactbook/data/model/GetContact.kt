package com.ahmed.contactbook.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetContact(
    val email: String?,
    val id: Int,
    val name: String,
    val notes: String?,
    val phones: List<GetPhone>
) : Parcelable