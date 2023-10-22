package com.busyprogrammer

import androidx.annotation.DrawableRes

data class Person(
    @DrawableRes val avatar: Int,
    val names: String,
    val occupations: String,
    val occupationDetails:String,
    val category: String
)