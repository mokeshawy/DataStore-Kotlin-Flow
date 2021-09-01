package com.example.datastorekotlin_flow.ui.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastorekotlin_flow.repository.DataStoreSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
 constructor(private val dataStoreSetting: DataStoreSetting ) : ViewModel() {

     fun writeToLocal(name : String ) = viewModelScope.launch {
         dataStoreSetting.writeToLocal(name)
     }

    val readToLocal = dataStoreSetting.readToLocal
}