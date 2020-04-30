package com.practice.ecansa.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.practice.ecansa.Model.userModel
import com.practice.ecansa.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.system.exitProcess

class RegisterActivity : AppCompatActivity() {

    lateinit var mAuth : FirebaseAuth
    lateinit var mDatabase : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()

        tvLoginRegister.setOnClickListener {
            goToLogin(this)
        }
        btnRegister.setOnClickListener(View.OnClickListener {
            getProperties(this)
        })

    }


    fun goToLogin(context: Context){
        var intent: Intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        this.onStop()
    }

    fun getProperties(context: Context){
        if (TextUtils.isEmpty(edtEmailRegister.text.toString().trim())||
            TextUtils.isEmpty(edtPasswordRegister.text.toString().trim())){

            if (TextUtils.isEmpty(edtEmailRegister.text.toString().trim())){
                edtEmailRegister.setError("Please Fill the Email")
            }
            if (TextUtils.isEmpty(edtPasswordRegister.text.toString().trim())){
                edtPasswordRegister.setError("Please Fill the Password")
            }
        }else {
            createUser(
                context,
                edtEmailRegister.text.toString().trim(),
                edtPasswordRegister.text.toString().trim(),
                mAuth
            )
        }
    }

    private fun createUser(context: Context,email:String,password:String,mAuth:FirebaseAuth){

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val currentUser = mAuth.currentUser
                    val uid = currentUser!!.uid
                    //val userMp = HashMap<String, userModel>()
                    //val userMap = HashMap<String,String>()
                    //userMap["Email"]=email
                    //userMp.put("Props",userModel(uid,email,password,"pembeli"))
//                   userMap.put("id",uid)
//                    userMap.put("email",email)
//                    userMap.put("status","pembeli")
//                    userMap.put("password",password)
                    mDatabase = FirebaseDatabase.
                        getInstance().
                        getReference("Email").
                        child(uid)



                    mDatabase.setValue(userModel(uid,email,password,"Pembeli"))
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){

                                val intent = Intent(context,LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                                Log.i("Message","Should Bo Going To Home")
                            }
                        }
                }else{
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Keluar Aplikasi")
        alertDialog.setMessage("Apakah anda yakin ingin keluar aplikasi ?")

        alertDialog.setPositiveButton("Iya") { dialog, which ->
            exitProcess(1)
        }
        alertDialog.setNegativeButton("Tidak") { dialog, which ->

        }
        alertDialog.create().show()
    }
}
