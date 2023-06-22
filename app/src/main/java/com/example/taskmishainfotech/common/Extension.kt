package com.example.taskmishainfotech.common

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.taskmishainfotech.R
import java.util.Random

fun Fragment.showToast(message:String?="Something went wrong"){
    Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
}

var Fragment.showLoader: Boolean
    get() = requireActivity().findViewById<View>(R.id.loader).isVisible
    set(value) {
        requireActivity().findViewById<View>(R.id.loader).isVisible = value
    }


fun generateRandomId():Int{
    val random = Random()
    val min = 1
    val max = 100
    return random.nextInt(max - min + 1) + min
}