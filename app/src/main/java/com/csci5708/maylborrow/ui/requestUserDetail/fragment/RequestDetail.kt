package com.csci5708.maylborrow.ui.requestUserDetail.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.ActivityLoginBinding
import com.csci5708.maylborrow.databinding.FragmentHomeBinding

import com.csci5708.maylborrow.databinding.FragmentRequestDetailBinding
import com.csci5708.maylborrow.ui.requestUserDetail.model.RequestUserDetail
import com.csci5708.maylborrow.ui.requestUserDetail.persistent.RecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.sql.Date
import java.text.SimpleDateFormat


class RequestDetail : Fragment() {


    private var _binding: FragmentRequestDetailBinding? = null
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView : RecyclerView
    private lateinit var recycleradapter : RecyclerAdapter
    private lateinit var detailModelLst : ArrayList<RequestUserDetail>


    var username : String? = null
    var postname: String? = null
    var date: String? = null
    var buyerEmail: String? = null
    var documentId: String? = null
    var sellerName: String? = null
    var sellerEmail: String? = null
    var sellerPhone: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentRequestDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noNotification = view.findViewById<TextView>(R.id.noNotification)
        noNotification.isVisible = false
        //getting the curreent logged in user
        auth = FirebaseAuth.getInstance()
        detailModelLst = arrayListOf<RequestUserDetail>()

        //get the recycler view
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        var noNotificationImage = view?.findViewById<ImageView>(R.id.notificationnoitems)

        db = FirebaseFirestore.getInstance()
        detailModelLst.clear()
        val sellerId1 = auth.currentUser?.email.toString()
        val contactDetail =  db.collection("requestContactDetails")
            .whereEqualTo("status", "Pending")
            .whereEqualTo("sellerId", sellerId1)
            .get()
        
        contactDetail.addOnSuccessListener { documents ->
            if(!documents.isEmpty){
            for (document in documents) {
                Log.d("tag1", "${document.id} => ${document.data} =>${document.data["sellerId"]}")

                //get the document id for updating status
                runBlocking {
                    launch {
                        documentId = getDocumentId(document.id)
                    }
                }

                //get the user name from the user table using coroutine
                val buyerId = document.data["buyerId"].toString().trim()
                Log.d("buyerId", buyerId)
                runBlocking {
                    launch { username = getUsername(buyerId) }
                }

                runBlocking {
                    launch { buyerEmail = getBuyerEmail(buyerId) }
                }

                //get the post title from Post table using coroutine
                val postId = document.data["postId"].toString().trim()
                println("postId1"+ postId)
                runBlocking {
                    launch { postname = getPostName(postId)
                    println("Postname"+postname)}
                }

                //get the user name from the user table using coroutine
                val sellerId = document.data["sellerId"].toString().trim()
                runBlocking {
                    launch {
                        val list: HashMap<String, String> = getSellerDetail(sellerId)
                        sellerName = list["name"]
                        sellerEmail = list["email"]
                        sellerPhone = list["phone"]
                    }
                }
                println("seller" + sellerName)
                //convert timestamp to datetime
                val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
                val timestamp = document.data["requestedOnDate"] as com.google.firebase.Timestamp
                val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                val netDate = Date(milliseconds)
                date = sdf.format(netDate).toString()

                detailModelLst.add(
                    RequestUserDetail(
                        username?.trim(),
                        postname?.trim(),
                        "Pending",
                        date?.trim(),
                        documentId?.trim(),
                        buyerEmail?.trim(),
                        sellerName?.trim(),
                        sellerPhone,
                        sellerEmail?.trim()
                    )
                )

                //bind recycler with the adapter
                recycleradapter = RecyclerAdapter(detailModelLst)
                recyclerView.adapter = recycleradapter
                recycleradapter.notifyDataSetChanged()


            }
        }else{
                noNotificationImage?.visibility = View.VISIBLE
                noNotification.isVisible = true
        }

        }.addOnFailureListener { exception ->
            Log.d("Tag", "Error occured")
        }
    }

    suspend fun getSellerDetail(sellerId: String): HashMap<String,String> {

        var sellerDetail= HashMap<String,String>()
        val user =  db.collection("user").document(sellerId).get().await()
        val firstName = user.data?.get("firstName").toString()
        val lastName = user.data?.get("lastName").toString()
        val email = user.data?.get("email").toString()
        val phone = user.data?.get("phoneNumber").toString()

        val seller_name = firstName+" "+lastName
        sellerDetail["name"] =seller_name
        sellerDetail["email"] = email
        sellerDetail["phone"] = phone
        return sellerDetail
    }

    //suspend function to get the username
    suspend fun getUsername(buyerId: String): String? {
       val user =  db.collection("User").document(buyerId).get().await()
       val firstName = user.data?.get("firstname").toString()
       val lastName = user.data?.get("lastname").toString()
        username = firstName+" "+lastName
        return username
    }

    //suspend function to get the username
    suspend fun getBuyerEmail(buyerId: String): String? {
        val user =  db.collection("User").document(buyerId).get().await()
        val email = user.data?.get("email").toString().trim()
        buyerEmail = email
        return buyerEmail
    }

    //suspend function to get the postname
    suspend fun getPostName(postId: String): String? {
        println("post"+postId)
        val itemname = view?.findViewById<TextView>(R.id.itemName)
        var title: String? = null
        val data = db.collection("post").whereEqualTo("productId",postId.trim()).limit(1).get().await()
        if(data.size() > 0)
        {
            val doc =  data.documents[0]
            title =  doc.get("postTitle").toString()
        }

        return title
    }


    //suspend function to get the document id
    suspend fun getDocumentId(docId: String): String?{
        return docId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
