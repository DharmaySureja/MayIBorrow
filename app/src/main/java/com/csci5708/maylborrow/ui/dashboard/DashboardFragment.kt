package com.csci5708.maylborrow.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.FragmentDashboardBinding
import com.csci5708.maylborrow.ui.home.DashBoardAdapter
import com.csci5708.maylborrow.ui.post.PostViewModel
import com.csci5708.maylborrow.ui.wishlist.Item
import com.google.firebase.firestore.*
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment() {



    private lateinit var recyclerView: RecyclerView
    private lateinit var itemLists: ArrayList<Item>
    private lateinit var adapter: DashBoardAdapter
    private lateinit var db: FirebaseFirestore
    private var _binding: FragmentDashboardBinding? = null
    var auth = FirebaseAuth.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.recyclerViewIdDashBoard)
        recyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        recyclerView.setHasFixedSize(true)
        itemLists = ArrayList()
        adapter = DashBoardAdapter(itemLists,this)
        recyclerView.adapter = adapter
        GetProducts()



        val addPostButton = binding.addPost
        addPostButton.setOnClickListener{
            //Go to second activity to create note
            val postViewModel = PostViewModel()
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_edit_post,  Bundle().apply {
                putParcelable("post_details",postViewModel)
            })
        }

        return root
    }

    private fun GetProducts(){

        val currentUser = auth.currentUser?.email
        db = FirebaseFirestore.getInstance()

        db.collection("post").whereEqualTo("userId",currentUser).addSnapshotListener(object: EventListener<QuerySnapshot> {

            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?){

                if(error != null){
                    Log.e("FireStore Error!", error.message.toString())
                    return
                }

                for(dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        val it = dc.document.toObject(Item:: class.java)
                        itemLists.add(it)
                    }
                }
                if(itemLists.isEmpty()){
                    var textview = view?.findViewById<TextView>(R.id.noproductTextView)
                    var noProductimage = view?.findViewById<ImageView>(R.id.noAddedProducts)
                    if (textview != null) {
                        textview.visibility = View.VISIBLE
                        noProductimage?.visibility = View.VISIBLE
                    }
                }

                adapter.notifyDataSetChanged()
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}