package com.example.taskmishainfotech.ui.storedata

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor():ViewModel() {
    val titleInput = MutableStateFlow("")

}