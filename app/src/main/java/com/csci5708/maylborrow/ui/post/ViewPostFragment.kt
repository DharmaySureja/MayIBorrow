package com.csci5708.maylborrow.ui.post


import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.FragmentViewPostBinding
import com.csci5708.maylborrow.ui.utils.Base64Utils
import com.csci5708.maylborrow.ui.utils.FragmentUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp

class ViewPostFragment : Fragment() {
    private var _binding: FragmentViewPostBinding? = null
    var db = FirebaseFirestore.getInstance()

    var auth = FirebaseAuth.getInstance()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val REMOVE_WISHLIST= "Remove Wishlist"
    private val ADD_WISHLIST= "Add to Wishlist"
    private val CONTACTED_SELLER = "Contacted Seller"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Post view model to set values to edit text in fragment
        val postViewModel = requireArguments().getParcelable<PostViewModel>("post_details")

        _binding = FragmentViewPostBinding.inflate(inflater, container, false)
        val view: View = binding.root
        val currentUser = auth.currentUser?.email
        val sellerUser = postViewModel?.userId?.value
        val productId = postViewModel?.productId?.value
        val requestData:Timestamp = Timestamp.now()
        val status = "Pending"
        val contactSellerButton: Button = view.findViewById<Button>(R.id.contact_seller)

        var rating1 : Float = 0f
        val ratingCurrent : RatingBar = binding.ratingBar
        // Fetch Product rating from fire base and populate stars according to rating
        if (postViewModel != null) {

            db.collection("rating").whereEqualTo("productId",postViewModel.productId.value).get()
                .addOnSuccessListener  { documents->
                    for (document in documents)
                    {
                        if(document["userid"].toString() == currentUser){
                            var userRatings : RatingBar = view.findViewById(R.id.userRatingbar)
                            var ratingSubmit : Button = view.findViewById(R.id.submitRatingBtn)
                            userRatings.setIsIndicator(true)
                            ratingSubmit.isEnabled = false
                            ratingSubmit.text= "You have already Submitted review"
                            userRatings.rating = document["rating"].toString().toFloat()
                        }
                        rating1 = rating1+ document["rating"].toString().toFloat()
                    }
                    rating1 = rating1 / documents.size()
                    ratingCurrent.rating=rating1
                }

        }



        db.collection("requestContactDetails")

        var userRatings : RatingBar = view.findViewById(R.id.userRatingbar)
        var ratingSubmit : Button = view.findViewById(R.id.submitRatingBtn)
        // submit rating of product
        ratingSubmit.setOnClickListener{
            Toast.makeText(context,"Thank you for providing Rating ", Toast.LENGTH_SHORT).show()
            // disable ratingbar and submit button once rating is submitted
            userRatings.setIsIndicator(true)
            ratingSubmit.isEnabled = false
            if (postViewModel != null) {
                // fetch current rating of product and calculate new rating and update firebase with new rating
//                db.collection("post").whereEqualTo("productId",postViewModel.productId.value).get()
//                    .addOnSuccessListener  { documents->
//                        for (document in documents) {
//                            var temp =  document["rating"].toString().toFloat()
//                            temp = (temp + userRatings.rating)/2
//                            var rateString = temp.toString()
//                            var docId = document.id
//                            val updateRating = mapOf("rating" to rateString)
//                            db.collection("post").document(docId).update(updateRating)
//
//                        }
//                    }
                val ratingdata = hashMapOf(
                    "userid" to currentUser,
                    "productId" to productId,
                    "rating" to userRatings.rating
                )
               db.collection("rating").add(ratingdata).addOnSuccessListener{
                   Toast.makeText(context,"SUBMITTED",Toast.LENGTH_SHORT)
               }
            }
        }



        // Enable or disable the Request Seller button according to records in database
        var CheckAlreadyRequested: Boolean = false

        db.collection("requestContactDetails")
             .whereEqualTo("postId",productId)
             .whereEqualTo("buyerId",currentUser)
             .get().addOnSuccessListener{ documents ->
                if(documents.count() > 0)   //already requested
                {
                    changeContactSellerButton()
                }
                else{
                    contactSellerButton.isEnabled = true
                }
            }

        contactSellerButton.setOnClickListener {

                val data = hashMapOf(
                    "buyerId" to currentUser,
                    "postId" to productId,
                    "requestedOnDate" to requestData,
                    "status" to status,
                    "sellerId" to sellerUser
                )
                println("*******************************%%")
                println(data)
                println("*******************************%%")
                db.collection("requestContactDetails")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        contactSellerButton.isEnabled = false
                        changeContactSellerButton()                 }
                    .addOnFailureListener { e ->
                        Toast.makeText(context,"Please check your internet connectivity", Toast.LENGTH_SHORT).show()                    }
                    }


        //when post is already added in wishlist
        if(postViewModel?.isAddedWishList?.value == true)
        {

           //set on click lister remove
            changeToRemoveWishlist()
            val addToWishListButton = view.findViewById<Button>(R.id.add_to_wishlist)
            val userEmail = auth.currentUser?.email;
            val productId = postViewModel?.productId?.value

            val data = hashMapOf(
                "userId" to userEmail,
                "productId" to productId
            )

            addToWishListButton.setOnClickListener{
                db.collection("wishlist").whereEqualTo("productId",productId).get()
                    .addOnSuccessListener  { documents->
                        for (document in documents) {

                            var docId = document.id
                            db.collection("wishlist").document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(context,"Remove from wishlist", Toast.LENGTH_SHORT).show()
                                    postViewModel.isAddedWishList.value = false
                                    changeToAddToWishList()

                                    FragmentUtils.refreshFragment(requireContext(),R.id.add_to_wishlist)
                                }
                                .addOnFailureListener { Toast.makeText(context,"Please check your internet connectivity", Toast.LENGTH_SHORT).show() }

                        }


                    }
            }




        }
        else {
            //set add wishlist
            val addToWishListButton = view.findViewById<Button>(R.id.add_to_wishlist)
            val userEmail = auth.currentUser?.email;
            val productId = postViewModel?.productId?.value

            val data = hashMapOf(
                 "userId" to userEmail,
                  "productId" to productId
            )

            addToWishListButton.setOnClickListener{
                db.collection("wishlist")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(context,"Added to Wishlist", Toast.LENGTH_SHORT).show()
                        if (postViewModel != null) {
                            postViewModel.isAddedWishList.value = true
                        }
                        changeToRemoveWishlist()
                        FragmentUtils.refreshFragment(requireContext(),R.id.add_to_wishlist)                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context,"Please check your internet connectivity", Toast.LENGTH_SHORT).show()                    }
            }

        }



        if (postViewModel != null) {
            setTextFields(postViewModel)
        }

        return view
    }

    fun setTextFields(postViewModel: PostViewModel)
    {
        //-- set title from view model --
        postViewModel?.title?.observe(viewLifecycleOwner) {
            binding.title.setText(it)
        }
        postViewModel?.description?.observe(viewLifecycleOwner) {
            binding.desc.setText(it)
        }
        postViewModel?.brand?.observe(viewLifecycleOwner) {
            binding.brand.setText(it)
        }
        postViewModel?.manufactureYear?.observe(viewLifecycleOwner) {
            binding.manufacturedYear.setText(it)
        }
        postViewModel?.pricePerDay?.observe(viewLifecycleOwner) {
            binding.pricePerDayText.setText("$it CAD per day")
        }
        postViewModel?.category?.observe(viewLifecycleOwner) {
            binding.category.setText(it)
        }
        postViewModel?.condition?.observe(viewLifecycleOwner) {
            binding.condition.setText(it)
        }
        postViewModel?.pickupAddress?.observe(viewLifecycleOwner) {
            binding.pickupAddress.setText(it)
        }
        postViewModel?.image?.observe(viewLifecycleOwner) {
            if(postViewModel.isUpload.value == true)
            {
                binding.itemImage.setImageBitmap(context?.let { it1 ->
                    Base64Utils.uriToBitmap_dirctURI(it,
                        it1
                    )
                })
            }
            else{
                binding.itemImage.setImageBitmap(Base64Utils.uriToBitmap(it))
            }
        }
    }

    fun changeToRemoveWishlist()
    {
        var addToWishList: Button = binding.addToWishlist
        addToWishList.setText(REMOVE_WISHLIST)
        addToWishList.setTextColor(resources.getColor(R.color.confirm_button))
        addToWishList.background = requireContext().resources.getDrawable(R.drawable.cancel_button)

    }

    fun changeToAddToWishList()
    {
        var addToWishList: Button = binding.addToWishlist
        addToWishList.setText(ADD_WISHLIST)
        addToWishList.setTextColor(resources.getColor(R.color.white))
        addToWishList.setBackgroundColor(resources.getColor(R.color.add_to_wishlist))
    }

    fun changeContactSellerButton(){
        var contactSellerButton: Button = binding.contactSeller
        contactSellerButton.isEnabled = false
        var view: View = binding.root
        contactSellerButton.visibility = View.INVISIBLE
        FragmentUtils.refreshFragment(requireContext(),R.id.contact_seller)
    }
}