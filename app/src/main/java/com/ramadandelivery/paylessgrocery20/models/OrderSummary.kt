package com.ramadandelivery.paylessgrocery20.models

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