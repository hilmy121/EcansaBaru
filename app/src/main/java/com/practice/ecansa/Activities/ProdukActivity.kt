package com.practice.ecansa.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import com.practice.ecansa.Adapter.TokoHolder
import com.practice.ecansa.Model.Makanan
import com.practice.ecansa.R
import com.squareup.picasso.Picasso

class ProdukActivity : AppCompatActivity() {
    lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var firebaseReference: DatabaseReference

    lateinit var dataset:ArrayList<Makanan>
    lateinit var recyler:RecyclerView
    lateinit var produkAdapter:FirebaseRecyclerAdapter<Makanan,TokoHolder>
    lateinit var reference:String
    companion object{
        val requestQuery : String = "911"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk)
        dataset = ArrayList<Makanan>()

        if (intent!=null) {
            reference= intent.getStringExtra(requestQuery)
        }
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseReference = firebaseDatabase.getReference("Toko").child(reference)
        recyler = findViewById(R.id.rvProduk)
        recyler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        setAdapter(recyler)
    }

    fun setAdapter(rv:RecyclerView){
         produkAdapter = object : FirebaseRecyclerAdapter<Makanan,TokoHolder>(Makanan::class.java,R.layout.item_produk,TokoHolder::class.java,firebaseReference){
             override fun populateViewHolder(tokoHolder: TokoHolder?, makanan: Makanan?, pos: Int) {
                 tokoHolder!!.tvItemHatgaProduk.text= makanan!!.Harga.toString()
                 tokoHolder.tvItemNamaProduk.text= makanan.Nama
                 Picasso.get().load(makanan.Image).into(tokoHolder.ivItem)
                 tokoHolder.view.setOnClickListener {
                    var intent = Intent(this@ProdukActivity,DetailProdukActivity::class.java)
                     intent.putExtra("Harga",makanan.Harga)
                     intent.putExtra("Nama",makanan.Nama)
                     intent.putExtra("Image",makanan.Image)
                     startActivity(intent)
                     finish()
                 }
             }

         }
        rv.adapter=produkAdapter
    }
}
