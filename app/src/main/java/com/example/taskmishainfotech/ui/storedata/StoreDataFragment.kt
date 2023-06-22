package com.example.taskmishainfotech.ui.storedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskmishainfotech.common.DB_NAME
import com.example.taskmishainfotech.common.generateRandomId
import com.example.taskmishainfotech.common.showLoader
import com.example.taskmishainfotech.common.showToast
import com.example.taskmishainfotech.databinding.FragmentStoreDataBinding
import com.example.taskmishainfotech.domain.TaskData
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class StoreDataFragment : Fragment() {

    private var _binding: FragmentStoreDataBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoreViewModel by viewModels()

    private val firebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setUpViewListener()
    }


    private fun setUpViewListener() {

        binding.addTask.setOnClickListener {
            addDataInFirebase()
        }
        binding.viewAllTask.setOnClickListener {
            val direction =
                StoreDataFragmentDirections.actionStoreDataFragmentToStoreDataListFragment()
            findNavController().navigate(direction)
        }
    }

    private fun addDataInFirebase() {
        val taskData = TaskData(
            generateRandomId().toString(),
            viewModel.titleInput.value,
            "", "", "", System.currentTimeMillis()
        )

        showLoader = true
        firebaseFirestore.collection(DB_NAME)
            .add(taskData)
            .addOnSuccessListener {
                showLoader = false
                lifecycleScope.launch {
                    showToast("Successfully Added")
                    Timber.d("Success ")
                }
            }
            .addOnFailureListener { e ->
                Timber.d("failure $e")
                showLoader = false
            }
    }


}