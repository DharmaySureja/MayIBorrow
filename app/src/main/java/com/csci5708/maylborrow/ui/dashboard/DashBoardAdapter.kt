package com.csci5708.maylborrow.ui.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.ui.dashboard.DashboardFragment
import com.csci5708.maylborrow.ui.post.PostViewModel
import com.csci5708.maylborrow.ui.utils.Conversion
import com.csci5708.maylborrow.ui.wishlist.Item

class DashBoardAdapter(private val productsList: ArrayList<Item>, private val dashboardFragment: DashboardFragment) :

    RecyclerView.Adapter<DashBoardAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_producthome_item, parent, false)
        val imageView = itemView.findViewById<ImageView>(R.id.ProductImage)

        imageView.setOnClickListener{
            dashboardFragment.findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_view_post, Bundle().apply {
                putParcelable("post_details", Conversion.convert_item_to_post_model(productsList[viewType]))
            })
        }

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Item = productsList[position]
        holder.productName.text = item?.postTitle
        holder.productPrice.text = "$"+item?.price

        val encodeByte = Base64.decode(item?.imageURL!!, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        holder.productImage.setImageBitmap(bitmap)

        holder.productImage.setOnClickListener{
            dashboardFragment.findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_my_view_post, Bundle().apply {
                var postViewModel : PostViewModel = Conversion.convert_item_to_post_model(item)
                putParcelable("post_details",postViewModel)
            })
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productImage: ImageView = itemView.findViewById(R.id.ProductImage)
        val productName: TextView = itemView.findViewById(R.id.ProductName)
        val productPrice: TextView = itemView.findViewById(R.id.ProductPrice)

    }
}