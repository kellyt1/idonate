package com.idonate.springboot.kotlin.demo

import java.util.*

data class Donation(var id : UUID? = null,
                    val name: String,
                    val donee: String,
                    val donor: String,
                    val type : Donation.Type = Type.Unknown) {

    enum class Type{
        Unknown,
        Gift
    }
}