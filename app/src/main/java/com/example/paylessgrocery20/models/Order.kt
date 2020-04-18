package com.example.paylessgrocery20.models


data class Order (
    //val userId : String = "",
    val orderID: String  ="",
    val orderStatus : String = "",
    val orderSummary : OrderSummary ?= null,
    //val user : User ?= null,
    val user:String,
    val shippingAddress : ShippingAddress?= null,
    val payment : Payment ?= null,
    val products : ArrayList<Product>,
    val date: String
)
{
    constructor() :this("","",OrderSummary("","","","","")
        ," ",ShippingAddress("","","","","",""),Payment("","",""),ArrayList<Product>(),""){

    }
}