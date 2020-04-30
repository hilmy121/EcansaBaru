package com.practice.ecansa.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.practice.ecansa.Fragments.AccountFragment
import com.practice.ecansa.Fragments.CartFragment
import com.practice.ecansa.Fragments.HistoryFragment
import com.practice.ecansa.Fragments.HomeFragment
import com.practice.ecansa.R
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        firebaseAuth = FirebaseAuth.getInstance()
        selectedFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,HomeFragment())
            .commit()

    }
    fun selectedFragment(){
        var navBottomListener : BottomNavigationView.OnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener {
                lateinit var selectedFragment: Fragment
                when(it.itemId){
                    R.id.navHome -> selectedFragment = HomeFragment()
                    R.id.navCart -> selectedFragment = CartFragment()
                    R.id.navHistory -> selectedFragment = HistoryFragment()
                    R.id.navAccount -> selectedFragment = AccountFragment()
                }
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,selectedFragment).commit()
                return@OnNavigationItemSelectedListener true
            }

        var btmNavHome:BottomNavigationView = findViewById(R.id.btmNavHome)
        btmNavHome.setOnNavigationItemSelectedListener(navBottomListener)

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
