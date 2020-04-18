package com.example.paylessgrocery20.models

import java.io.Serializable

data class OrderSummary (
    val totalAmount : String ?= null,
    val ourPrice : String?= null,
    val discount : String?= null,
    val deliveryCharges : String?= null,
    val orderAmount : String
){
    constructor():this("","","","",""){

    }
}