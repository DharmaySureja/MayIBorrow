package com.csci5708.maylborrow.ui.post

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostViewModel() : ViewModel(), Parcelable{

    private val _title = MutableLiveData<String>().apply {
        value = ""
    }
    val title: MutableLiveData<String> = _title

    private val _description = MutableLiveData<String>().apply {
        value = ""
    }
    val description: MutableLiveData<String> = _description

    private val _brand = MutableLiveData<String>().apply {
        value = ""
    }
    val brand: MutableLiveData<String> = _brand

    private val _manufactureYear = MutableLiveData<String>().apply {
        value = ""
    }
    val manufactureYear: MutableLiveData<String> = _manufactureYear

    private val _pickupAddress = MutableLiveData<String>().apply {
        value = ""
    }
    val pickupAddress: MutableLiveData<String> = _pickupAddress

    private val _pricePerDay = MutableLiveData<String>().apply {
        value = ""
    }
    val pricePerDay: MutableLiveData<String> = _pricePerDay

    private val _condition = MutableLiveData<String>().apply {
        value = ""
    }
    val condition: MutableLiveData<String> = _condition

    private val _category = MutableLiveData<String>().apply {
        value = ""
    }
    val category: MutableLiveData<String> = _category

    private val _image = MutableLiveData<Uri>().apply {
        value = null
    }
    val image: MutableLiveData<Uri> = _image

    private val _userId = MutableLiveData<String>().apply {
        value = ""
    }
    val userId: MutableLiveData<String> = _userId

    private val _quantity = MutableLiveData<String>().apply {
        value = ""
    }
    val quantity: MutableLiveData<String> = _quantity


    private val _rating = MutableLiveData<String>().apply {
        value = "-1"
    }
    val rating: MutableLiveData<String> = _rating

    private val _status = MutableLiveData<String>().apply {
        value = "0"
    }
    val status: MutableLiveData<String> = _status

    private val _productId = MutableLiveData<String>().apply {
        value = ""
    }
    val productId: MutableLiveData<String> = _productId

    private val _isAddedWishList = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isAddedWishList: MutableLiveData<Boolean> = _isAddedWishList

    private val _isUpload = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isUpload: MutableLiveData<Boolean> = _isUpload

    /**
     * returns "" if the fields for the post
     */
    fun isPostComplete(): String
    {
        if(title.value?.isEmpty() == true)
        {
            return "Title cannot be empty"
        }
        else if (description.value?.isEmpty() == true)
        {
            return "Description cannot be empty"
        }
        else if(category.value?.isEmpty() == true)
        {
            return "Category cannot be empty"
        }
        else if(condition.value?.isEmpty() == true)
        {
            return "Condition cannot be empty"
        }
        else if(pickupAddress.value?.isEmpty() == true)
        {
            return "Pickup Address cannot be empty"
        }
        else if(pricePerDay.value?.isEmpty() == true)
        {
            return "Price per day cannot be empty"
        }
        else if(image.value == null)
        {
            return "Please upload an image of the item"
        }
        else if(manufactureYear.value?.toInt()!! < 1950 || manufactureYear.value?.toInt()!! > 2022)
        {
            return "Please enter a valid year"
        }
        else if(pricePerDay.value?.toInt()!! < 0)
        {
            return "Please enter a valid price"
        }
        else if(quantity.value?.isEmpty() == true)
        {
            return "Quantity cannot be empty"
        }
        else    //all fields are correct
        {
            return ""
        }

    }

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostViewModel> {
        override fun createFromParcel(parcel: Parcel): PostViewModel {
            return PostViewModel(parcel)
        }

        override fun newArray(size: Int): Array<PostViewModel?> {
            return arrayOfNulls(size)
        }
    }
}
