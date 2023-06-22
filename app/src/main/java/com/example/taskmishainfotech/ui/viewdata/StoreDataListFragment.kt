package com.example.taskmishainfotech.ui.viewdata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmishainfotech.common.DB_NAME
import com.example.taskmishainfotech.common.removeItemDialog
import com.example.taskmishainfotech.common.showData
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
    private val storeListAdapter = StoreListAdapter(listener = ::onItemCLick)
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
        val itemTouchHelper = ItemTouchHelper(swipeItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.taskRecyclerView)
        binding.taskRecyclerView.apply {
            adapter = storeListAdapter
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            itemAnimator = DefaultItemAnimator()
        }
        showLoader = true
        getFirebaseData(firebaseFirestore)

    }

    //Callback for itemSwipe
    private var swipeItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            //on item swipe show pop-up to aware user
            val position = viewHolder.adapterPosition
            if (taskList.isNotEmpty()) removeItemDialog("Alert! ") {
                taskList.removeAt(position)
                storeListAdapter.submitList(taskList)
            }
        }
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

    private fun onItemCLick(taskData: TaskData, position: Int) {
        with(taskData) {
            Timber.d("value of tasdata is $title")
            showData(title, description, imageUrl) {

            }
        }
    }

}