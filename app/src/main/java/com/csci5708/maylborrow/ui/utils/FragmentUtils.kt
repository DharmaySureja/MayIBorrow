package com.csci5708.maylborrow.ui.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class FragmentUtils {
    companion object {
        fun refreshFragment(context: Context?, id: Int)
        {
            context?.let {
                val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
                fragmentManager?.let {
                    var currentFragment = fragmentManager.findFragmentById(id)
                    currentFragment?.let {
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.detach(it)
                        fragmentTransaction.attach(it)
                        fragmentTransaction.commit()
                    }
                }
            }
        }
    }

}