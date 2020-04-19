package com.example.paylessgrocery20.app

class Endpoints {

    companion object {

        private const val URL_LOGIN = "auth/login"
        private const val URL_REGISTER = "auth/register"
        private const val URL_CATEGORY = "category"
        private const val URL_SUBCATEGORY = "subcategory/"
        private const val URL_PRODUCT = "products/sub/"

        fun getLogin():String{
            return Config.BASE_URL + URL_LOGIN
        }
        fun getRegister():String{
            return Config.BASE_URL + URL_REGISTER
        }
        fun getCategory():String{
            return Config.BASE_URL + URL_CATEGORY
        }
        fun getSubCategoryByCatID():String{
            return Config.BASE_URL + URL_SUBCATEGORY
        }
        fun getProductBySubID():String{
            return Config.BASE_URL + URL_PRODUCT
        }

    }
}