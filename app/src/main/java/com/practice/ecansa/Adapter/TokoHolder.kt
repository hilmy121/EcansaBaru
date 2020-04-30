package com.practice.ecansa.Adapter

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practice.ecansa.R
import kotlinx.android.synthetic.main.item_produk.view.*

class TokoHolder(itemview:View) : RecyclerView.ViewHolder(itemview) {
    var tvItemHatgaProduk : TextView
    var tvItemNamaProduk : TextView
    var ivItem : ImageView
    var view : RelativeLayout
    init {
        tvItemHatgaProduk = itemview.findViewById(R.id.tvItemHargaProduk)
        tvItemNamaProduk = itemview.findViewById(R.id.tvItemNamaProduk)
        ivItem=itemview.findViewById(R.id.ivItem)
        view = itemView.findViewById(R.id.makanan)
    }
}