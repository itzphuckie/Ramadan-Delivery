package com.ramadandelivery.paylessgrocery20.models

import java.io.Serializable

data class Category (
    val catImage : String? = null,
    val catDescription : String? = null,
    val position : Int?=0,
    val status : Boolean? = null,
    val _id : String? = null,
    val catId : Int = 0,
    val catName : String? = null,
    val slug : String? = null,
    val __v : Int? = 0
):Serializable