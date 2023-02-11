package com.csci5708.maylborrow.ui.wishlist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.FragmentWishlistBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


class WishlistFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var itemList: ArrayList<Item>
    private lateinit var adapter: Adapter
    private lateinit var db: FirebaseFirestore
    private val productIdList = ArrayList<String>();
    var  auth = FirebaseAuth.getInstance()



    private var _binding: FragmentWishlistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val wishlistViewModel =
            ViewModelProvider(this).get(WishlistViewModel::class.java)

        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.recyclerViewIdWishlist)

        recyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        recyclerView.setHasFixedSize(true)
        itemList = arrayListOf()
        adapter = Adapter(itemList,this)
        recyclerView.adapter = adapter

        eventChangeListener();
        GetProducts();



        return root
    }

    private fun eventChangeListener() {
        val userId = auth.currentUser?.email;
        db = FirebaseFirestore.getInstance()
        db.collection("wishlist").addSnapshotListener(object: EventListener<QuerySnapshot> {

            override fun onEvent(value: QuerySnapshot?,error: FirebaseFirestoreException?){
                if(error != null){
                    Log.e("FireStore Error!", error.message.toString())
                    return
                }
                for(dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
//
                        val it = dc.document.toObject(Item:: class.java)
                        if(it.userId == userId){
                            val tmp: String  = it.productId!!
                            productIdList?.add(tmp)
                        }
                    }
                }
//                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun GetProducts(){


        db.collection("post").addSnapshotListener(object: EventListener<QuerySnapshot>{

            override fun onEvent(value: QuerySnapshot?,error: FirebaseFirestoreException?){

                if(error != null){
                    Log.e("FireStore Error!", error.message.toString())
                    return
                }

                for(dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        val it = dc.document.toObject(Item:: class.java)
                        if(productIdList.contains(it.productId)){
                            itemList.add(it)
                        }
                    }
                }
                //chane text
                if (itemList.isEmpty()){
                    var textview = view?.findViewById<TextView>(R.id.noproductTextView)
                    var wishlistimage = view?.findViewById<ImageView>(R.id.noAddedProducts)
                    if (textview != null) {
                        textview.visibility = View.VISIBLE
                        wishlistimage?.visibility = View.VISIBLE
                    }
                }
                adapter.notifyDataSetChanged()
            }

        })
    }


//    It was leading to error: So we have commented it
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.searchbar,menu)
//
//        val menuItem : MenuItem? = menu.getItem(R.id.search_for_products)
//        val searchView : SearchView = menuItem?.actionView as SearchView
//        searchView.queryHint="Type here to search posts"
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//
//        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}