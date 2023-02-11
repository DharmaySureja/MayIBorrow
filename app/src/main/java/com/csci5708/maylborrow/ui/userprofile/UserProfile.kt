package com.csci5708.maylborrow.ui.userprofile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.FragmentUserProfileBinding
import com.csci5708.maylborrow.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore



class UserProfile : Fragment() {

    //fragment
    private var _binding: FragmentUserProfileBinding? = null
    lateinit var viewModel: UserProfileViewModel
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
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)



        //set the data for viewModel
        viewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)

        //to read the data from the database
        getUserProfile(viewModel)
        val editButton = view.findViewById<ImageButton>(R.id.editButton)

        //set dummy image
        val imageView: ImageView? = view?.findViewById(R.id.imageView)
        imageView?.setImageResource(R.drawable.ic_profile_black_24dp)

        //set the values in edit text
        val firstname = view?.findViewById<TextView>(R.id.profile_firstname)
        viewModel.firstName.observe(viewLifecycleOwner, androidx.lifecycle.Observer { (
                firstname?.setText(it.toString())
        ) })

        //set the values in edit text
        val lastname = view?.findViewById<TextView>(R.id.profile_lastname)
        viewModel.lastName.observe(viewLifecycleOwner, androidx.lifecycle.Observer { (
                lastname?.setText(it.toString())
        ) })

        //set the values in edit text
        val phoneNumbers = view?.findViewById<TextView>(R.id.profile_mobilenumber)
        viewModel.phoneNumber.observe(viewLifecycleOwner, androidx.lifecycle.Observer { (
                phoneNumbers?.setText(it.toString())
        ) })

        //set the values in edit text
        val address = view?.findViewById<TextView>(R.id.profile_address)
        viewModel.address.observe(viewLifecycleOwner, androidx.lifecycle.Observer { (
                address?.setText(it.toString())
        ) })

        //set the values in edit text
        editButton.setOnClickListener {
            //val bundle = bundleOf("Profile" to dictionary)
            findNavController().navigate(R.id.action_navigation_profile_to_navigate_edit_user_profile)
        }
//        val backButton = view.findViewById<ImageButton>(R.id.backButton)
//        backButton.setOnClickListener {
//            findNavController().navigate(R.id.action_navigation_profile_to_navigation_home)
//        }
        var btnlogout : Button = view.findViewById(R.id.btnlogout)
        btnlogout.setOnClickListener {
            val preferences: SharedPreferences =
                requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

       // return inflater.inflate(R.layout.fragment_user_profile, container, false)
        return view
    }

    private fun getUserProfile(viewModel: UserProfileViewModel) {
        val auth = FirebaseAuth.getInstance()
        val email = auth.currentUser?.email.toString().trim()

        //get the data from database
        var user_data : Map<Any, Any> = emptyMap()
        val db = FirebaseFirestore.getInstance()

        db.collection("User").whereEqualTo("email",email).get()
            .addOnSuccessListener { documents->
                for(document in documents)
                {

                    val firstName = document["firstname"].toString()
                    this.viewModel.firstName.value = firstName

                    val lastName = document["lastname"].toString()
                    this.viewModel.lastName.value = lastName

                    val phoneNumber = document["phonenumber"].toString()
                    this.viewModel.phoneNumber.value = phoneNumber

                    val address = document["address"].toString()
                    this.viewModel.address.value = address                }

            }
            .addOnFailureListener{
                Toast.makeText(requireContext(),"Please check your internet connectivity ",Toast.LENGTH_LONG)
            }
    }



}



