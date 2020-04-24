package com.ramadandelivery.paylessgrocery20.helpers

import android.content.Context

class SessionManager(var mContext: Context) {

    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    companion object {
        val FILE_NAME: String = "share_pref"
        val KEY_NAME: String = "name"
        val KEY_EMAIL: String = "email"
        val KEY_PASSWORD: String = "password"
        val KEY_IS_LOGGED_IN: String = "isLogedIn"
        val KEY_IS_USERNAME_SAVED: String = "isUsernamSaved"
    }

    fun saveUser(email: String) {
        editor.putString(KEY_EMAIL,email)
        editor.commit()
    }

//    fun login(email: String): Boolean{
//        var user = getUser()
//        return user.email.equals(email)
//    }
    fun checkSaveUsernameStatus(isSaved:Boolean): Boolean{
    return sharedPreferences.getBoolean(KEY_IS_USERNAME_SAVED, isSaved)
}

    fun checkLoginStatus(): Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUser(): String {
        var name = sharedPreferences.getString(KEY_NAME, null)
        //var user = User(, email, password)
        return name.toString()
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }


}