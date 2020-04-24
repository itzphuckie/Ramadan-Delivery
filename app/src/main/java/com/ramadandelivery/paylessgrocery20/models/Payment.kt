package com.ramadandelivery.paylessgrocery20.models

import java.io.Serializable

data class Payment(
    val paymentMode : String,
    val paymentNumber:String,
    val paymentStatus : String
):Serializable{
    constructor():this("","",""){

    }
}