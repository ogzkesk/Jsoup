package com.example.jsoupapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsoupapp.models.News
import com.example.jsoupapp.util.Constants.IMAGE_URL
import com.example.jsoupapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val list = mutableListOf<News>(
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",1),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",2),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",3),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",4),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",5),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",6),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",7),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",8),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",9),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",10),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",11),
        News("Title 1","Description 1",IMAGE_URL,"12.13.2022",12),
    )

    private val _listDummy = MutableStateFlow<UiState<List<News>>?>(null)
    val listDummy = _listDummy.asStateFlow()

    private fun setListDummy() = viewModelScope.launch {
        _listDummy.value = UiState.Loading()
        delay(2000)
        _listDummy.value = UiState.Success(list)
    }

    // ********************


    private val _dummyImage = MutableStateFlow<UiState<String>?>(null)
    val dummyImage = _dummyImage.asStateFlow()

    private fun setDummyImage() = viewModelScope.launch {
        _dummyImage.value = UiState.Loading()
        delay(2000)
        _dummyImage.value = UiState.Success(IMAGE_URL)
    }

    fun resetDummyImage() = viewModelScope.launch {
        _dummyImage.value = null
        setDummyImage()
        setListDummy()
    }


}