package com.csci5708.maylborrow.ui.requestUserDetail.persistent

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.ui.requestUserDetail.model.RequestUserDetail
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text


class RecyclerAdapter(private val userDetilList : ArrayList<RequestUserDetail>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var userDetail = emptyList<RequestUserDetail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val detailListView : View = layoutInflater.inflate(R.layout.contactdetail_view, parent, false)
        return ViewHolder(detailListView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db = FirebaseFirestore.getInstance()
        val currentuserDetail = userDetilList[position]
        println("user detail"+userDetail)
        holder.buyerName?.text = currentuserDetail.buyerName
        holder.postDetail?.text = currentuserDetail.postName
        holder.date?.text = currentuserDetail.createdDate
        holder.docId?.text = currentuserDetail.docId
        holder.buyerEmail?.text = currentuserDetail.buyerEmail
        holder.sellerName?.text = currentuserDetail.sellerName
        holder.sellerEmail?.text = currentuserDetail.sellerEmail
        holder.sellerPhone?.text = currentuserDetail.sellerContactNumber

        holder.accept?.setOnClickListener {
            var docIds = holder.docId?.text?.toString()?.trim()
            if (docIds != null) {


                db.collection("requestContactDetails").document(docIds)
                    .update("status", "Active")
                    .addOnSuccessListener {
                        val email: String = holder.buyerEmail?.text.toString()
                        val postname: String = holder.postDetail?.text.toString()
                        val buyerName: String = holder.buyerName?.text.toString()
                        val sellerName: String = holder.sellerName?.text.toString()
                        val sellerEmail: String = holder.sellerEmail?.text.toString()
                        val sellerPhone: String = holder.sellerPhone?.text.toString()

                        sendEmail(email,postname,buyerName,sellerName, sellerEmail,sellerPhone, holder)
                    }
            }
        }

        holder.reject?.setOnClickListener {
            var docIds = holder.docId?.text?.toString()?.trim()
            Log.d("docId2",docIds.toString())
            if (docIds != null) {
                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setTitle("Request Alert!!")
                builder.setMessage("Are you sure you want to cancel request?")

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    db.collection("requestContactDetails").document(docIds)
                        .update("status", "Reject")
                    Toast.makeText(holder.itemView.context,
                       "Request Rejected", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(holder.itemView.context,
                        "OK! Request Active", Toast.LENGTH_SHORT).show()
                }

                builder.show()

            }
        }
    }


    inner class ViewHolder(requestdetailItemView : View?) : RecyclerView.ViewHolder(requestdetailItemView!!){

        val buyerName : TextView? = requestdetailItemView?.findViewById(R.id.buyerName)
        val postDetail : TextView? = requestdetailItemView?.findViewById(R.id.itemName)
        val date : TextView? = requestdetailItemView?.findViewById(R.id.date)
        val docId : TextView? = requestdetailItemView?.findViewById(R.id.docId)
        val buyerEmail : TextView? = requestdetailItemView?.findViewById(R.id.email)
        val sellerName : TextView? = requestdetailItemView?.findViewById(R.id.sellerName)
        val sellerEmail : TextView? = requestdetailItemView?.findViewById(R.id.sellerEmail)
        val sellerPhone : TextView? = requestdetailItemView?.findViewById(R.id.sellerPhoneNumber)
        val accept : Button? = requestdetailItemView?.findViewById(R.id.accept)
        val reject : Button? = requestdetailItemView?.findViewById(R.id.reject)
        var contactDetailPosition = 0

    }
    override fun getItemCount(): Int {
        return userDetilList.size
    }

    private fun sendEmail(email: String, postname : String, buyerName: String
    ,sellerName: String, sellerEmail: String, sellerPhone: String, holder: ViewHolder) {

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:"+email)
            putExtra(Intent.EXTRA_EMAIL, email)
            putExtra(Intent.EXTRA_SUBJECT, "Your request for " + postname + " is accepted")
            putExtra(
                Intent.EXTRA_TEXT,
                "Hello " + buyerName + "\n Your request to borrow " + postname + " " +
                        "was accepted by " + sellerName + ".\n\n\nHis Contact Number is: " + sellerPhone +
                        "or you can reach out to him via " + sellerEmail + "."
            )
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        holder.itemView.context.startActivity(intent)

    }


    fun setUserDetail(userDetail : List<RequestUserDetail>){
        this.userDetail = userDetail;
        notifyDataSetChanged()
    }


}