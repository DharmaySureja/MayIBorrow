package com.csci5708.maylborrow.ui.userprofile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.FragmentUserprofileEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EditUserProfile : Fragment() {

    private var _binding: FragmentUserprofileEditBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val auth = FirebaseAuth.getInstance()
         val view = inflater.inflate(R.layout.fragment_userprofile_edit, container, false)

        //set the data for viewModel
        val viewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)

        //set dummy image
        val imageView: ImageView? = view?.findViewById(R.id.imageView)
        imageView?.setImageResource(R.drawable.ic_profile_black_24dp)

        val firstname = view?.findViewById<TextView>(R.id.profile_firstname)
        viewModel.firstName.observe(viewLifecycleOwner, androidx.lifecycle.Observer { (
                firstname?.setText(it.toString())
        ) })

        val lastname = view?.findViewById<TextView>(R.id.profile_lastname)
        viewModel.lastName.observe(viewLifecycleOwner, androidx.lifecycle.Observer { (
                lastname?.setText(it.toString())
        ) })

        val phoneNumbers = view?.findViewById<TextView>(R.id.profile_mobilenumber)
        viewModel.phoneNumber.observe(viewLifecycleOwner, androidx.lifecycle.Observer { (
                phoneNumbers?.setText(it.toString())
        ) })

        val address = view?.findViewById<TextView>(R.id.profile_address)
        viewModel.address.observe(viewLifecycleOwner, androidx.lifecycle.Observer { (
                address?.setText(it.toString())
        ) })


        //adding validation when user changes his profile detail
        val saveChangeButton = view?.findViewById<Button>(R.id.saveChangeButton)
        //checking the not null field when making changes
        saveChangeButton?.setOnClickListener {

            val first_name: String = firstname?.text.toString()
            val last_name: String = lastname?.text.toString()
            val phone_Number: String = phoneNumbers?.text.toString()
            val address_1: String = address?.text.toString()
            //check if the EditText have values or not
            if (first_name.trim().isEmpty()) {
                firstname?.error = "Required"
            } else if(first_name.trim().length == 1){
                firstname?.error = "Enter Valid FirstName"
            }else if (last_name.trim().isEmpty()) {
                lastname?.error = "Required"
            }else if(last_name.trim().length == 1) {
                lastname?.error = "Enter Valid Last Name"
            }else if (phone_Number.trim().isEmpty()) {
                phoneNumbers?.error = "Required"
                Toast.makeText(context, "Phone Number Required ", Toast.LENGTH_SHORT).show()
            } else if (phoneNumbers?.text?.length != 10) {
                //number should be equal to 10
                phoneNumbers?.error = "Must be 10 digits"
            } else if (address_1.trim().isEmpty()) {
                address?.error = "Required"
            } else if (address_1.trim().length == 1) {
                address?.error = "Enter Valid address"
            }else {
              saveData(first_name, last_name, phone_Number, address_1, auth)
            }
        }
        _binding = FragmentUserprofileEditBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return view
    }

    //update the employee data in firestore database
    private fun saveData(first_name : String, last_name: String, phone_Number: String, address_1: String, auth: FirebaseAuth){


        val db = FirebaseFirestore.getInstance()
        val user = mapOf(
            "firstname" to first_name,
            "lastname" to last_name,
            "phonenumber" to phone_Number,
            "address" to address_1
        )
        var useremail = auth.currentUser?.email.toString()
        db.collection("User").document(useremail)
            .update(user).addOnSuccessListener {
                Toast.makeText(context, "Changes Saved! ", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_navigate_edit_user_profile_to_navigation_profile)
            }.addOnFailureListener {
                Toast.makeText(context, "Error Changing the profile! ", Toast.LENGTH_SHORT)
                    .show()
            }
    }

}