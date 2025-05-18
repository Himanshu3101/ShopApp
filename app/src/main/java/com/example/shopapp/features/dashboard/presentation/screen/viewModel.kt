package com.example.shopapp.features.dashboard.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.util.Resources
import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class dashboardViewModel @Inject constructor(
    private val getBanner_UC: GetBanner_UC,
): ViewModel(){



    init {
        getBanner()
    }

   /* fun onEvent(){

    }*/

    private fun getBanner() {
        viewModelScope.launch {
            getBanner_UC().collect{ resources ->

                when(resources){
                    is Resources.Loading/*<*>*/ -> {
                        Log.d("Banner", "loading")
                    }
                    is Resources.Success/*<*>*/ ->{
                        Log.d("Banner", resources.data.toString())
                    }
                    is Resources.Erro/*<*>*/ ->  {
                        Log.d("Banner", resources.message.toString())
                    }
                }

            }
        }
    }
}