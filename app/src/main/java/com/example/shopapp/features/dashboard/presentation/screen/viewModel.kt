package com.example.shopapp.features.dashboard.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetCategory_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetItems_UC
import com.example.shopapp.features.dashboard.presentation.screen.event.ev_dashboard
import com.example.shopapp.features.dashboard.presentation.screen.state.ItemData
import com.example.shopapp.features.dashboard.presentation.screen.state.st_Dashboard
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class dashboardViewModel @Inject constructor(
    private val getBanner_UC: GetBanner_UC,
    private val getCategory_UC: GetCategory_UC,
    private val getItems_UC: GetItems_UC
) : ViewModel() {

    private val _dashboardState = MutableStateFlow(st_Dashboard())
    val dashboardState = _dashboardState.asStateFlow()

    fun onEvent(event: ev_dashboard) {
        when (event) {
            is ev_dashboard.getBanners -> {
                getBanner()
            }

            ev_dashboard.getCategory -> {
                getCategory()
            }

            ev_dashboard.getItens -> {
                getItems()
            }
        }
    }

    private fun getItems() {
        viewModelScope.launch {
            getItems_UC().collect { resources ->
                when(resources) {
                    is Resources.Error -> {

                        _dashboardState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        Log.d("Items", resources.message.toString())

                    }
                    is Resources.Loading -> {
                        _dashboardState.update { it.copy(isLoading = true) }
                    }
                    is Resources.Success -> {
                        val itemList = resources.data ?: emptyList()

                        val mappedData = itemList.map{
                            ItemData(
                                imageUrl = it.picUrl,
                                price = it.price,
                                rating = it.rating,
                                title = it.title
                            )
                        }

                        Log.e("viewModelGetItems", mappedData.toString())

                        _dashboardState.update {
                            it.copy(
                                itemsState = mappedData,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCategory() {
        viewModelScope.launch {
            getCategory_UC().collect { resources ->

                when (resources) {
                    is Resources.Loading -> {
                        _dashboardState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        val categories = resources.data?.map { it.title } ?: emptyList()
                        _dashboardState.update {
                            it.copy(
                                isLoading = false,
                                categoryList = categories
                            )
                        }
                    }

                    is Resources.Error -> {
                        _dashboardState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        Log.d("Banner", resources.message.toString())
                    }
                }

            }
        }
    }

    private fun getBanner() {
        viewModelScope.launch {
            getBanner_UC().collect { resources ->

                when (resources) {
                    is Resources.Loading -> {
                        _dashboardState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        val urls = resources.data?.map { it.url } ?: emptyList()
                        _dashboardState.update {
                            it.copy(
                                isLoading = false,
                                bannerUrls = urls
                            )
                        }
                    }

                    is Resources.Error -> {
                        _dashboardState.update {
                            it.copy(
                               isLoading = false
                            )
                        }
                        Log.d("Banner", resources.message.toString())
                    }
                }

            }
        }
    }
}



