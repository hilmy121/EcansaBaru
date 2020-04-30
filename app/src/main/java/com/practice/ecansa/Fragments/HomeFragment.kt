package com.practice.ecansa.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.practice.ecansa.Activities.LoginActivity
import com.practice.ecansa.Activities.ProdukActivity

import com.practice.ecansa.R

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var txtBasket: TextView
    lateinit var txtVolly: TextView
    lateinit var txtBaadminton: TextView
    lateinit var txtBaseball: TextView
    lateinit var cardBasket: CardView
    lateinit var cardVolly: CardView
    lateinit var cardBadminton: CardView
    lateinit var cardBaseball: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view =inflater.inflate(R.layout.fragment_home, container, false)
        cardVolly = view.findViewById(R.id.cardVolly)
        cardBaseball = view.findViewById(R.id.cardBaseball)
        cardBasket = view.findViewById(R.id.cardBasket)
        cardBadminton = view.findViewById(R.id.cardBadminton)
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardBasket.setOnClickListener {
            var intent = Intent(view.context, ProdukActivity::class.java)
            intent.putExtra(ProdukActivity.requestQuery,"Stand Basket")
            startActivity(intent)
        }
        cardBadminton.setOnClickListener {
            var intent = Intent(view.context, ProdukActivity::class.java)
            intent.putExtra(ProdukActivity.requestQuery,"Stand Badminton")
            startActivity(intent)
        }
        cardBaseball.setOnClickListener {
            var intent = Intent(view.context, ProdukActivity::class.java)
            intent.putExtra(ProdukActivity.requestQuery,"Stand Baseball")
            startActivity(intent)
        }
        cardVolly.setOnClickListener {
            var intent = Intent(view.context, ProdukActivity::class.java)
            intent.putExtra(ProdukActivity.requestQuery,"Stand Volly")
            startActivity(intent)
        }
    }
}
