package com.ahmed.contactbook.data.model

data class GetContact(
    val email: String?,
    val id: Int,
    val name: String,
    val notes: String?,
    val phones: List<GetPhone>
)