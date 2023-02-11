package com.csci5708.maylborrow.ui.utils

import android.net.Uri
import com.csci5708.maylborrow.ui.home.ProductItem
import com.csci5708.maylborrow.ui.post.PostViewModel
import com.csci5708.maylborrow.ui.wishlist.Item

class Conversion {
    companion object {
        /**
         * Convert the Item object into PostViewModel
         */
        fun convert_item_to_post_model(item: Item): PostViewModel
        {
            var postModelView = PostViewModel()

            postModelView.brand.value = item.brand
            postModelView.condition.value = item.condition
            postModelView.category.value = item.categoryid
            postModelView.description.value = item.description
            postModelView.image.value =  Uri.parse(item.imageURL)
            postModelView.manufactureYear.value = item.manufactureYear
            postModelView.pickupAddress.value = item.pickUpAddress
            postModelView.title.value = item.postTitle
            postModelView.pricePerDay.value = item.price
            postModelView.quantity.value = item.quantity
            postModelView.rating.value = item.rating
            postModelView.userId.value = item.userId
            postModelView.productId.value = item.productId
            postModelView.status.value = item.status

            return postModelView
        }

        fun convert_item_to_home_post_model(item: ProductItem): PostViewModel
        {
            var postModelView = PostViewModel()

            postModelView.brand.value = item.brand
            postModelView.condition.value = item.condition
            postModelView.category.value = item.categoryid
            postModelView.description.value = item.description
            postModelView.image.value =  Uri.parse(item.imageURL)
            postModelView.manufactureYear.value = item.manufactureYear
            postModelView.pickupAddress.value = item.pickUpAddress
            postModelView.title.value = item.postTitle
            postModelView.pricePerDay.value = item.price
            postModelView.quantity.value = item.quantity
            postModelView.rating.value = item.rating
            postModelView.userId.value = item.userId
            postModelView.productId.value = item.productId
            postModelView.status.value = item.status

            return postModelView
        }
    }
}