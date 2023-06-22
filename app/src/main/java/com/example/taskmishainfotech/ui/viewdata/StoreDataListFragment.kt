package com.example.taskmishainfotech.ui.viewdata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmishainfotech.common.DB_NAME
import com.example.taskmishainfotech.common.showLoader
import com.example.taskmishainfotech.databinding.FragmentStoreDataListBinding
import com.example.taskmishainfotech.domain.TaskData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StoreDataListFragment : Fragment() {
    private var _binding: FragmentStoreDataListBinding? = null
    private val binding get() = _binding!!
    private val firebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val storeListAdapter = StoreListAdapter()
    private lateinit var registration: ListenerRegistration
    private lateinit var taskList: MutableList<TaskData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreDataListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewListener()
    }

    private fun setUpViewListener() {
        taskList = mutableListOf()
        binding.taskRecyclerView.apply {
            adapter = storeListAdapter
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            itemAnimator = DefaultItemAnimator()
        }
        showLoader = true
        getFirebaseData(firebaseFirestore)


    }

    private fun getFirebaseData(db: FirebaseFirestore) {
        val collectionRef = db.collection(DB_NAME)
        registration = collectionRef.addSnapshotListener { querySnapshot, exception ->
            showLoader = false
            if (exception != null) {
                Timber.e(exception)
                return@addSnapshotListener
            }
            taskList.clear()
            for (document in querySnapshot!!) {
                val user = document.toObject(TaskData::class.java)
                taskList.add(user)
            }
            storeListAdapter.submitList(taskList.sortedByDescending { it.timestamp })
        }
    }

}