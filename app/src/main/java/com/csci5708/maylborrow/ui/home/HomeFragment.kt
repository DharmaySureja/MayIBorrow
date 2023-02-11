package com.csci5708.maylborrow.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.FragmentHomeBinding
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.*
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemList: ArrayList<ProductItem>
    private lateinit var adapter: ProductAdapter
    private lateinit var db: FirebaseFirestore
    private var _binding: FragmentHomeBinding? = null
    var auth = FirebaseAuth.getInstance()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root 

        recyclerView = root.findViewById(R.id.recyclerViewIdMain)

        recyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        recyclerView.setHasFixedSize(true)
        itemList = arrayListOf()
        adapter = ProductAdapter(itemList,this)
        recyclerView.adapter = adapter

        eventChangeListener();

        val sortByOptions = resources.getStringArray(R.array.SortBy)

        val sortByDropdown = root.findViewById<Spinner>(R.id.sortByDropdown)

        if(sortByDropdown!=null){
            val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,sortByOptions)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sortByDropdown.adapter=adapter

            sortByDropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val chosenSortFilter :String = parent?.getItemAtPosition(position) as String
                    if(chosenSortFilter != "Sort by:"){
                        if(chosenSortFilter == "Name") itemList.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.postTitle.toString() })
                        if(chosenSortFilter == "Price") itemList.sortBy { it.price?.toInt() }
                        updateAdapterData()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }

        return root
    }

    private fun updateAdapterData(){
        adapter.updateData(itemList)
    }

    private fun eventChangeListener() {
        val currentUser = auth.currentUser?.email
        db = FirebaseFirestore.getInstance()
        db.collection("post").whereNotEqualTo("userId",currentUser).addSnapshotListener(object: EventListener<QuerySnapshot> {

            override fun onEvent(value: QuerySnapshot?,error: FirebaseFirestoreException?){
                if(error != null){
                    Log.e("FireStore Error!", error.message.toString())
                    return
                }
                for(dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        val it = dc.document.toObject(ProductItem:: class.java)
                        if(dc.document.get("postTitle")!=null || dc.document.get("price")!=null || dc.document.get("imageURL")!=null){
                            itemList.add(it)
                        }
                    }
                    var textview = view?.findViewById<TextView>(R.id.noproductTextview)
                    var noProductimage = view?.findViewById<ImageView>(R.id.noproductsimageview)
                    var sortHide = view?.findViewById<MaterialCardView>(R.id.materialCard)
                    if(itemList.isEmpty()){

                        if (textview != null) {
                            textview.visibility = View.VISIBLE
                            noProductimage?.visibility = View.VISIBLE
                            sortHide?.visibility= View.INVISIBLE
                        }

                    }
                    else{
                        textview?.visibility = View.INVISIBLE
                        noProductimage?.visibility = View.INVISIBLE
                        sortHide?.visibility= View.VISIBLE
                    }

                }
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchbar,menu)

        val menuItem : MenuItem? = menu.findItem(R.id.search_for_products)
        val searchView = menuItem?.actionView as SearchView
        searchView.queryHint="Type here to search posts"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchProduct(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if(newText.length==0){
                        searchProduct("")
                    }
                }
                return false
            }
        })
    }

    private fun searchProduct(keyword:String) {
        val currentUser = auth.currentUser?.email
        db = FirebaseFirestore.getInstance()
        db.collection("post").whereNotEqualTo("userId",currentUser).addSnapshotListener(object: EventListener<QuerySnapshot> {

            override fun onEvent(value: QuerySnapshot?,error: FirebaseFirestoreException?){
                if(error != null){
                    Log.e("FireStore Error!", error.message.toString())
                    return
                }
                itemList.clear()
                for(dc : DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){
                        val productName = dc.document.get("postTitle")
                        val productPrice = dc.document.get("price")
                        val productURL = dc.document.get("imageURL")
                        var it = dc.document.toObject(ProductItem::class.java)
                        if(productName!=null || productPrice!=null || productURL!=null){
                            if(productName.toString().contains(keyword,ignoreCase = true)){
                                itemList.add(it)
                            }
                        }
                    }
                }
                var textview = view?.findViewById<TextView>(R.id.noproductTextview)
                var noProductimage = view?.findViewById<ImageView>(R.id.noproductsimageview)
                var sortHide = view?.findViewById<MaterialCardView>(R.id.materialCard)
                if(itemList.isEmpty()){

                    if (textview != null) {
                        textview.visibility = View.VISIBLE
                        noProductimage?.visibility = View.VISIBLE
                        sortHide?.visibility= View.INVISIBLE
                    }

                }
                else{
                    textview?.visibility = View.INVISIBLE
                    noProductimage?.visibility = View.INVISIBLE
                    sortHide?.visibility= View.VISIBLE
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
