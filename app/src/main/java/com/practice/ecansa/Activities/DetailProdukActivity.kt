package com.practice.ecansa.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import com.practice.ecansa.Adapter.TokoHolder
import com.practice.ecansa.Model.Makanan
import com.practice.ecansa.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_produk.*

class DetailProdukActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)

        namaProduk.text=intent.getStringExtra("Nama")
        hargaProduk.text= intent.getIntExtra("Harga",0).toString()
        Picasso.get().load(intent.getStringExtra("Image")).into(imgDetail)
    }

}
