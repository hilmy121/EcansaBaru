package com.practice.ecansa.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.practice.ecansa.Model.userModel
import com.practice.ecansa.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var SP:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SP = getSharedPreferences("Login",Context.MODE_PRIVATE)

        val status = SP.getString("STATUS","")

        firebaseAuth = FirebaseAuth.getInstance()
        tvRegisterLogin.setOnClickListener {
            goToRegister(this)
        }
        btnLogin.setOnClickListener {
            val usern = edtEmailLogin.text.toString()
            val pass = edtPasswordLogin.text.toString()

            if(usern.isNullOrEmpty()){
                edtEmailLogin.error = "Harap isi username"
                edtEmailLogin.requestFocus()
            }else if(pass.isNullOrEmpty()){
                edtPasswordLogin.error = "Harap isi password"
                edtPasswordLogin.requestFocus()
            }else{
                loginFirebase(this@LoginActivity,usern,pass)
                //loginUser(this@LoginActivity,usern,pass)
            }

        }

    }

    fun goToRegister(context: Context) {
        var intent: Intent = Intent(context, RegisterActivity::class.java)
        startActivity(intent)
        this.onStop()
    }

    private fun loginFirebase(context: Context,email: String, password:String){

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Masukan alamat email!", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Masukan password!", Toast.LENGTH_SHORT)
                    .show()

            }
        }else{
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    val ref = FirebaseDatabase.getInstance().getReference("Email").child(
                        firebaseAuth.currentUser!!.uid)
                    ref.addListenerForSingleValueEvent(object:ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()){
                            val userMap = dataSnapshot.getValue(userModel::class.java)

                                if (userMap!!.status=="Pembeli"){
                                    startActivity(Intent(context, HomeActivity::class.java))
                                    finish()
                                }else{
                                    startActivity(Intent(context, TokoActivity::class.java))
                                    finish()
                                }
                            }else{
                                Toast.makeText(applicationContext,"blm punya akun",Toast.LENGTH_LONG).show()
                            }
                        /*if (dataSnapshot.exists()){
                            for(u in dataSnapshot.children){
                                val user = u.getValue(userModel::class.java)
                                if (user!!.password.equals(password)){
                                    if (user.status=="Pembeli"){
                                        startActivity(Intent(context, HomeActivity::class.java))
                                        finish()
                                    }else{
                                        startActivity(Intent(context, TokoActivity::class.java))
                                        finish()
                                    }
                                }else{
                                    Toast.makeText(applicationContext,"password salah",Toast.LENGTH_LONG).show()
                                }

                            }
                        }else{
                            Toast.makeText(applicationContext,"blm punya akun",Toast.LENGTH_LONG).show()

                        }*/
                        }

                    })
                }
            }
        }

    }

    private fun loginUser(context: Context,email: String, password:String){
        val ref = FirebaseDatabase.getInstance().reference.child("Email").orderByChild("email").equalTo(email)
        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
            if (p0.exists()){
                for (u in p0.children){
                    val user = u.getValue(userModel::class.java)
                    if (user!!.password.equals(password)){
                        val editor = SP.edit()
                        editor.putString("email",user.email)
                        editor.putString("status",user.status)
                        editor.putString("id",user.id)
                        editor.apply()
                        if (user.status=="Pembeli"){
                            startActivity(Intent(context, HomeActivity::class.java))
                            finish()
                        }else{
                            startActivity(Intent(context, TokoActivity::class.java))
                            finish()
                        }


                    }else{
                        Toast.makeText(applicationContext,"password salah",Toast.LENGTH_LONG).show()
                    }

                }
            }else{
                Toast.makeText(applicationContext,"blm punya akun",Toast.LENGTH_LONG).show()

            }

            }

        })
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
/*    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        tvRegisterLogin.setOnClickListener {
            goToRegister(this)
        }
        btnLogin.setOnClickListener {
            login(firebaseAuth,this)
        }

    }

    fun goToRegister(context: Context) {
        var intent: Intent = Intent(context, RegisterActivity::class.java)
        startActivity(intent)
        this.onStop()
    }

    fun login(firebaseAuth: FirebaseAuth,context: Context) {
        if (TextUtils.isEmpty(edtEmailLogin.text.toString().trim()) ||
            TextUtils.isEmpty(edtPasswordLogin.text.toString().trim())
        ) {

            if (TextUtils.isEmpty(edtEmailLogin.text.toString().trim())) {
                edtEmailLogin.setError("Please Fill the Email")
            }
            if (TextUtils.isEmpty(edtPasswordLogin.text.toString().trim())) {
                edtPasswordLogin.setError("Please Fill the Password")
            }
        }
        firebaseAuth.signInWithEmailAndPassword(edtEmailLogin.text.toString().trim(),
            edtPasswordLogin.text.toString().trim()).addOnCompleteListener {
            if (it.isSuccessful){
                lateinit var firebaseUser: FirebaseUser
                firebaseUser  = firebaseAuth.currentUser!!
                //var statusUser:String = firebas

            }


        }

    }*/
 /*  lateinit var SP:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SP = getSharedPreferences("Login",Context.MODE_PRIVATE)

        val status = SP.getString("STATUS","")

        if(status == "desa"){
            startActivity(Intent(this, desaActivity::class.java))
            finish()
        }else if(status == "rt"){
            startActivity(Intent(this, rtActivity::class.java))
            finish()
        }


        btnLogin.setOnClickListener {
            val usern = edtEmailLogin.text.toString()
            val pass = edtPasswordLogin.text.toString()

            if(usern.isNullOrEmpty()){
                edtEmailLogin.error = "Harap isi username"
                edtEmailLogin.requestFocus()
            }else if(pass.isNullOrEmpty()){
                edtPasswordLogin.error = "Harap isi password"
                edtPasswordLogin.requestFocus()
            }else{
                loginUser(usern,pass)
            }

        }



    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Keluar Aplikasi")
        alertDialog.setMessage("Apakah anda yakin ingin keluar aplikasi ?")

        alertDialog.setPositiveButton("Iya") { dialog, which ->
            finish()
        }
        alertDialog.setNegativeButton("Tidak") { dialog, which ->

        }
        alertDialog.create().show()
    }

    private fun loginUser(username: String,pass:String){
        val ref = FirebaseDatabase.getInstance().reference.child("Email").orderByChild("email").equalTo(edtEmailLogin)
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (u in p0.children){
                        val user = u.getValue(userModel::class.java)
                        if(user!!.pass.equals(pass)){
                            val editor = SP.edit()
                            editor.putString("email",user.username)
                            editor.putString("status", user.status)
                            editor.putString("id", user.id)
                            editor.apply()
                            if(user.status =="desa"){
                                val intent = Intent(this@mainActivity,desaActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(this@mainActivity,rtActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                        }else{
                            Toast.makeText(applicationContext,"Password salah", Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    Toast.makeText(applicationContext,"Akun tidak terdaftar", Toast.LENGTH_LONG).show()
                }
            }

        })
    }

*/



}
