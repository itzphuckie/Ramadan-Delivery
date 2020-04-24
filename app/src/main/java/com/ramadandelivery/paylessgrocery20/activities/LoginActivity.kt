package com.ramadandelivery.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.app.Users
import com.ramadandelivery.paylessgrocery20.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
//    lateinit var mGoogleSignInClient: GoogleSignInClient
//    lateinit var googleSignInOption:GoogleSignInOptions
    var user = Users.USERNAME_LOGIN_REMEMBER
    lateinit var userDetails:User
    lateinit var auth: FirebaseAuth
    lateinit var email : String
    val RC_SIGN_IN = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //sessionManager = SessionManager(this)
        auth = FirebaseAuth.getInstance()
        init()
        forgotPassword()
        new_user_text_view.setOnClickListener {
            var intentNewUser= Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intentNewUser)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResults(task)

        }
    }

    private fun handleResults(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account:GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
            updateUI(account)

        }catch (e: ApiException){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        startActivity(Intent(this,CategoryActivity::class.java))
    }

    private fun forgotPassword(){
        check_box_forgot_pass.setOnClickListener {
            startActivity(Intent(this,
                RequestPasswordActivity::class.java))
        }
    }
    private fun init() {
        signin_button.setOnClickListener {
            var email = login_name.text.toString()
            var password = login_password.text.toString()
            if(email.isEmpty()){
                Toast.makeText(applicationContext,"Email cannot be blank", Toast.LENGTH_SHORT).show()
            }
            if(password.isEmpty()){
                Toast.makeText(applicationContext,"Password cannot be blank", Toast.LENGTH_SHORT).show()
            }
            //if(email!=null && password!=null){
            else{
                //email = saveUsername
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,object:
                    OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if(task.isSuccessful){
                            var sharePref = getSharedPreferences("emailID", Context.MODE_PRIVATE)
                            var editor = sharePref.edit()
                            editor.putString("email",email)
                            editor.apply()
                            startActivity(Intent(this@LoginActivity,CategoryActivity::class.java))
                            ///userDetails.email = email
                            Toast.makeText(applicationContext,"Login Successfully", Toast.LENGTH_SHORT).show()
                            //sessionManager.saveUser(email)
                            finish()
                        }
                        else{
                            Toast.makeText(applicationContext,"Wrong email or password", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
                startActivity(Intent(this@LoginActivity,CategoryActivity::class.java))
            }
            }
    }

}
