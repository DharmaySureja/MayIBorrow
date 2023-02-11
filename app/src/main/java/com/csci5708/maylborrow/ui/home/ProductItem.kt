package com.csci5708.maylborrow.ui.home

import com.google.firebase.Timestamp

data class ProductItem(
    var brand: String?= null,
    var categoryid: String?= null,
    var condition: String?= null,
    var description: String?= null,
    var imageURL: String?= null,
    var manufactureYear: String?= null,
    var pickUpAddress: String?= null,
    var postTitle: String?= null,
    var postedOn: Timestamp?= null,
    var price: String?= null,
    var quantity: String?= null,
    var rating: String?= null,
    var status: String?= null,
    var userId: String?= null,
    var productId: String ?= null
)

