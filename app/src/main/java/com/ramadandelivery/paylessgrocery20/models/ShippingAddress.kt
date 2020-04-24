package com.ramadandelivery.paylessgrocery20.models

import java.io.Serializable

data class ShippingAddress (
    //val pincode : Int,
    val shippingName: String,
    val address: String,
    val city : String,
    val state: String,
    val zipcode: String,
    val phone:String
):Serializable{
    constructor():this("","","","","",""){

    }
}