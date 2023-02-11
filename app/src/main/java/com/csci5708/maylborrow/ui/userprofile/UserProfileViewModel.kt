package com.csci5708.maylborrow.ui.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class UserProfileViewModel : ViewModel(){


   val firstName: MutableLiveData<String> by lazy{
      MutableLiveData<String>()
    }

    val lastName: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    val phoneNumber: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    val address: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
}



