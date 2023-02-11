package com.csci5708.maylborrow.ui.post


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.FragmentMyViewPostBinding
import com.csci5708.maylborrow.ui.utils.Base64Utils
import com.google.firebase.firestore.FirebaseFirestore


class ViewMyPostFragment : Fragment() {
    private var _binding: FragmentMyViewPostBinding? = null
    private var postViewModel: PostViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Post view model to set values to edit text in fragment
        postViewModel = requireArguments().getParcelable<PostViewModel>("post_details")

        _binding = FragmentMyViewPostBinding.inflate(inflater, container, false)
        val view: View = binding.root

        postViewModel?.let { setTextFields(it) }


        val editPostButton = binding.editPost
        editPostButton.setOnClickListener{
            //Go to edit post fragment
            findNavController().navigate(R.id.action_navigation_my_view_post_to_navigation_edit_post, Bundle().apply {
                putParcelable("post_details",postViewModel)
                })

        }

        var rating1 : Float = 0f
        val ratingCurrent : RatingBar = binding.postRatingbar

        if (postViewModel != null) {

            db.collection("rating").whereEqualTo("productId",postViewModel?.productId?.value).get()
                .addOnSuccessListener  { documents->
                    for (document in documents)
                    {

                        rating1 = rating1+ document["rating"].toString().toFloat()
                    }
                    rating1 = rating1 / documents.size()
                    ratingCurrent.rating=rating1
                }

        }



        var deletePostBtn : Button = view.findViewById(R.id.delete_post)
        deletePostBtn.setOnClickListener {
            // Confirm user with alert dialogue
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    if (postViewModel != null) {
                        // delete post from firebase using document id fetched using product id
                        db.collection("post").whereEqualTo("productId",postViewModel?.productId?.value).get()
                            .addOnSuccessListener  { documents->
                                for (document in documents) {

                                    var docId = document.id
                                    db.collection("post").document(docId)
                                        .delete()
                                        .addOnSuccessListener {
                                            Toast.makeText(context,"Post Deleted", Toast.LENGTH_SHORT).show()
                                            findNavController().navigate(R.id.action_navigation_my_view_post_to_navigation_dashboard)
                                        }
                                        .addOnFailureListener { Toast.makeText(context,"Error Deleting Post", Toast.LENGTH_SHORT).show() }

                                }
                            }
                    }
        }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Extract the Post details from firebase
        db.collection("post")
            .whereEqualTo("postTitle",postViewModel?.title?.value)
            .whereEqualTo("description",postViewModel?.description?.value)
            .whereEqualTo("pickUpAddress", postViewModel?.pickupAddress?.value).get()
            .addOnSuccessListener{documents->
                for(document in documents)  //retrtived doucments from the post
                {
                    postViewModel?.productId?.value = document["productId"].toString()
                }

            }

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
}