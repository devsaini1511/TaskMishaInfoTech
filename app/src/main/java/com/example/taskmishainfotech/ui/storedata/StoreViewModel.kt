package com.example.taskmishainfotech.ui.storedata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor():ViewModel() {
    val titleInput = MutableStateFlow("")
    val imageUrl =
        MutableStateFlow("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg")
    val descriptionInput = MutableStateFlow("")

    fun setDescriptionInput(value: String) {
        viewModelScope.launch {
            descriptionInput.emit(value)
        }
    }
}