package com.example.paylessgrocery20.models

import java.io.Serializable

data class User(
    var email : String,
    val mobile : String,
    //val _id : String,
    val firstName : String
    //val password : String ?= null

    //val created : String,
    //val address : List<String>,
    //val __v : Int
):Serializable
