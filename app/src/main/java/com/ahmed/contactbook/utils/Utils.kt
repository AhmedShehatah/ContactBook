package com.ahmed.contactbook.utils

import android.content.Context
import android.widget.Toast

enum class SharedKeyEnum {
    TOKEN,
    FIRST_LOGIN
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}