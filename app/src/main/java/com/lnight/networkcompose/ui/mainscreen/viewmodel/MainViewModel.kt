package com.lnight.networkcompose.ui.mainscreen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnight.networkcompose.model.data.remote.response.Dog
import com.lnight.networkcompose.model.repository.DogRepository
import com.lnight.networkcompose.model.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DogRepository
): ViewModel() {


    var dogList = mutableStateOf<List<Dog>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var isSearching = mutableStateOf(true)

    init {
        loadDogs()
    }

    fun loadDogs() {
        Timber.tag("TAG").e("Load Dogs called")
        viewModelScope.launch {
            isLoading.value = true
            isSearching.value= false
            val result = repository.getData()
            when(result) {
                is Resource.Success -> {
                    val dogsEntries = result.data!!
                    Timber.tag("TAG").e("data int viewModel -> ${dogsEntries.message}")
                    loadError.value = ""
                    isLoading.value = false
                    dogList.value += dogsEntries
                }
                is Resource.Error -> {
                    Timber.tag("TAG").e("result error")
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }


}