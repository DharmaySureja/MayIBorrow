package com.csci5708.maylborrow.ui.post


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.FragmentEditPostBinding
import com.csci5708.maylborrow.ui.utils.Base64Utils
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.google.firebase.auth.FirebaseAuth

const val CHOOSE_CONDITION="Choose Condition"
const val POOR="Poor"
const val FAIR="Fair"
const val GOOD="Good"
const val LIKE_NEW="Like New"
const val NEW="New"
const val EDIT_POST_TITLE = "Edit Post"

class EditPostFragment : Fragment() {
    private var _binding: FragmentEditPostBinding? = null
    private lateinit var db: FirebaseFirestore
    private var categoryList = ArrayList<String>()
    var auth = FirebaseAuth.getInstance()
    private var postViewModel: PostViewModel? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditPostBinding.inflate(inflater, container, false)

        //firebase instance
        db = FirebaseFirestore.getInstance()

        categoryList.add("Choose Category")

        val view: View = binding.root


        var conditionList = ArrayList<String>()
        conditionList.add(CHOOSE_CONDITION)
        conditionList.add(POOR)
        conditionList.add(FAIR)
        conditionList.add(GOOD)
        conditionList.add(LIKE_NEW)
        conditionList.add(NEW)


        val conditionSpinner = view.findViewById<Spinner>(R.id.condition)
        customizeSpinner(conditionSpinner, conditionList)

        //load from firestore from categories collection
        val categories = db.collection("category").get()
        categories.addOnSuccessListener { documents ->

            for (document in documents) {
                Log.d("tag1", "${document.id} => ${document.data} =>${document.data["categoryName"]}")

                categoryList.add(document["categoryName"].toString().trim())
            }

            val categorySpinner = view.findViewById<Spinner>(R.id.category)
            customizeSpinner(categorySpinner, categoryList)

            //set text fields value
            //Empty if the page is for add the post
            postViewModel?.let { setTextFields(it, categoryList, conditionList) }

        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(),"Cannot connect to server. Please check internet connectivity.",Toast.LENGTH_LONG)
        }

        //Post view model to set values to edit text in fragment
        postViewModel = requireArguments().getParcelable<PostViewModel>("post_details")

        //If the fragment is called to Edit the existing post
        if(postViewModel?.title?.value?.isEmpty() != true)
        {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = EDIT_POST_TITLE
        }


        //Set image to viewmodel
        var resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                // Suppose you have an ImageView that should contain the image:
                postViewModel?.image?.postValue(imageUri)
                postViewModel?.isUpload?.value = true
                binding.filename.setText("Image Added")
            }
        }

        val create_post_button = view.findViewById<Button>(R.id.choose_file)
        create_post_button.setOnClickListener{
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            resultLauncher.launch("image/*")
        }


        //Update the view model before saving the data
        val confirmChangesButton = binding.confirmChanges
        confirmChangesButton.setOnClickListener{

            //modify fields updated
            if(postViewModel != null)
            {
                postViewModel?.title?.value = binding.postTitle.text.toString()
                postViewModel?.pricePerDay?.value = (binding.pricePerDay.text.toString())
                postViewModel?.pickupAddress?.value = (binding.pickupAddress.text.toString())
                postViewModel?.brand?.value = (binding.brand.text.toString())
                postViewModel?.description?.value = (binding.postDesc.text.toString())
                postViewModel?.manufactureYear?.value = (binding.manufacturedYear.text.toString())
                postViewModel?.category?.value = (binding.category.selectedItem.toString())
                postViewModel?.condition?.value = (binding.condition.selectedItem.toString())
                postViewModel?.quantity?.value = (binding.quantity.text.toString())
            }


            //checks if any field is missing or not .. show error
            val isPostComplete: String? = postViewModel?.isPostComplete()
            if(isPostComplete!!.isEmpty())
            {
                postViewModel?.let { it1 -> writePostOnFirestore(it1) }
            }
            else{
                Toast.makeText(requireContext(), isPostComplete ,Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    fun setTextFields(postViewModel: PostViewModel, categoryList: ArrayList<String>, conditionList: ArrayList<String>)
    {
        //-- set title from view model --
        val postTitleEditText: EditText = binding.postTitle
        postViewModel!!.title.observe(viewLifecycleOwner) {
            postTitleEditText.setText(it)
        }

        //-- set description from view model --
        val postDescEditText: EditText = binding.postDesc
        postViewModel!!.description.observe(viewLifecycleOwner) {
            postDescEditText.setText(it)
        }

        //-- set brand from view model --
        val brandText: EditText = binding.brand
        postViewModel!!.brand.observe(viewLifecycleOwner) {
            brandText.setText(it)
        }

        //-- set manu_year from view model --
        val manufacture_year_text: EditText = binding.manufacturedYear
        postViewModel!!.manufactureYear.observe(viewLifecycleOwner) {
            manufacture_year_text.setText(it)
        }

        //-- set pickupaddress from view model --
        val pickup_addr_text: EditText = binding.pickupAddress
        postViewModel!!.pickupAddress.observe(viewLifecycleOwner) {
            pickup_addr_text.setText(it)
        }

        //-- set price per day from view model --
        val price_per_day_text: EditText = binding.pricePerDay
        postViewModel!!.pricePerDay.observe(viewLifecycleOwner) {
            price_per_day_text.setText(it)
        }

        //-- set quantity --
        val quantity: EditText = binding.quantity
        postViewModel!!.quantity.observe(viewLifecycleOwner) {
            quantity.setText(it)
        }

        //set spinner
        postViewModel!!.category.observe(viewLifecycleOwner) {
            val position = categoryList.indexOf(it)

            if(position != -1)
            {
                binding.category.setSelection(position)
            }
        }

        //setspinner
        postViewModel!!.condition.observe(viewLifecycleOwner) {
            val position = conditionList.indexOf(it)
            if(position != -1)
            {
                binding.condition.setSelection(position)
            }
        }
    }

    /**
     * customize spinner with list of elements
     */
    fun customizeSpinner(spinner: Spinner, adapterList: ArrayList<String>)
    {
        if (spinner != null) {
            val adapter = object : ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item, adapterList
            ){

                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                    if(position == 0) {
                        view.setTextColor(Color.GRAY)
                    } else {
                    }
                    return view
                }

            }

            spinner.adapter = adapter
        }
    }


    /**
     * Write the post to firestore
     */
    fun writePostOnFirestore(postViewModel: PostViewModel)
    {
        val currentUser = auth.currentUser?.email
        val post = HashMap<String, String?>()
        post["postTitle"] = postViewModel.title.value
        post["description"] = postViewModel.description.value
        post["manufactureYear"] = postViewModel.manufactureYear.value
        post["brand"] = postViewModel.brand.value
        post["categoryid"] = postViewModel.category.value
        post["condition"] = postViewModel.condition.value
        post["pickUpAddress"] = postViewModel.pickupAddress.value
        post["price"] = postViewModel.pricePerDay.value
        post["quantity"] = postViewModel.quantity.value
        post["rating"] = "0"
        post["userId"] = currentUser
        post["status"] = "Active"
        post["imageURL"] = Base64Utils.imageToBase64(postViewModel.image.value, requireContext().contentResolver)

        if(postViewModel.productId.value.toString()?.isEmpty() == true)     //Product does not exist so add the product
        {
            post["productId"] = UUID.randomUUID().toString()
            db.collection("post")
                .document(post["productId"]!!)
                .set(post)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Your post is successfully created",Toast.LENGTH_LONG)
                    findNavController().navigate(R.id.action_navigation_edit_post_to_navigation_view_post,
                        Bundle().apply {putParcelable("post_details",postViewModel)})

                }.addOnFailureListener{
                    Toast.makeText(requireContext(),"Failed. Please check internet connectivity",Toast.LENGTH_LONG)
                }
        }
        else{           //product exists so update the product
            post["productId"] = postViewModel.productId.value.toString()
            db.collection("post")
                .document(post["productId"]!!)
                .update(post as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Your post is successfully created",Toast.LENGTH_LONG)
                    findNavController().navigate(R.id.action_navigation_edit_post_to_navigation_view_post,
                        Bundle().apply {putParcelable("post_details",postViewModel)})

                }.addOnFailureListener{
                    Toast.makeText(requireContext(),"Failed. Please check internet connectivity",Toast.LENGTH_LONG)
                }
        }
    }

}