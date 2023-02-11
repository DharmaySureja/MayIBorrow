package com.csci5708.maylborrow.ui.wishlist

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csci5708.maylborrow.R


//class Adapter_sukaran_error(private val productList: ArrayList<Item>) :
//    RecyclerView.Adapter<Adapter.MyViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_producthome_item, parent, false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item: Item = productList[position]
//        holder.productName.text = item?.productName
//        holder.productPrice.text = item?.productPrice
//
//        val productURL = item?.productURL
//        if(productURL!=null){
//            val index: Int= productURL.indexOf(",")
//            var pureBase64Encoded: String = item?.productURL!!.substring(index)
//            val encodeByte = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
//            val bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.size)
//            Thread.sleep(100)
//
//            holder.productImage.setImageBitmap(bitmap)
//        }
//        else{
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return productList.size
//    }
//
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        val productImage: ImageView = itemView.findViewById(R.id.ProductImage)
//        val productName: TextView = itemView.findViewById(R.id.ProductName)
//        val productPrice: TextView = itemView.findViewById(R.id.ProductPrice)
//
//    }
//}
