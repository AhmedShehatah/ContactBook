package com.ahmed.contactbook.data.model


data class SaveContact(
    val email: String?,
    val name: String,
    val notes: String?,
    val phones: List<Phone>
)