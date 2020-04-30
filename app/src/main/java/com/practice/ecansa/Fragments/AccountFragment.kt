package com.practice.ecansa.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.practice.ecansa.Activities.HomeActivity
import com.practice.ecansa.Activities.LoginActivity
import com.practice.ecansa.Activities.ProdukActivity

import com.practice.ecansa.R
import kotlinx.android.synthetic.main.fragment_account.*

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var btnLogout: Button
    lateinit var tvDisplayEmailAkun:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_account, container, false)
        btnLogout = view.findViewById(R.id.btnLogout)
        tvDisplayEmailAkun = view.findViewById(R.id.tvDisplayEmailAkun)
        if (FirebaseAuth.getInstance().currentUser!=null){
        tvDisplayEmailAkun.text= FirebaseAuth.getInstance().currentUser!!.email
        }
        return view}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogout.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser!=null) {
                FirebaseAuth.getInstance().signOut()
                var intent = Intent(view.context, LoginActivity::class.java)

                startActivity(intent)
            }
        }
    }


}
