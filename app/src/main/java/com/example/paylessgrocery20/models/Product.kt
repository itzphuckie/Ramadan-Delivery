package com.example.paylessgrocery20.models

import java.io.Serializable

data class Product (

    val description : String ?= null,
    //val status : Boolean? = null,
    //val position : Int,
    //val created : String? = null,
    //val _id : String? = null,
    //val catId : Int? = null,
    //val subId : Int ? = null,
    val productName : String,
    var quantity:Int = 0,
    var price : Double = 0.0,
    var mrp : Double = 0.0,
    val image : String
    //val unit : String? = null,

    //val __v : Int = 0
):Serializable{
    constructor():this("","",0,0.0,0.0,""){
        
    }
}