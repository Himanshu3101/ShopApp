package com.example.shopapp.features.dashboard.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetCategory_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetItems_UC
import com.example.shopapp.features.dashboard.presentation.screen.event.ev_dashboard
import com.example.shopapp.features.dashboard.presentation.screen.state.CategoryDetails
import com.example.shopapp.features.dashboard.presentation.screen.state.ItemData
import com.example.shopapp.features.dashboard.presentation.screen.state.st_Dashboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getBanner_UC: GetBanner_UC,
    private val getCategory_UC: GetCategory_UC,
    private val getItems_UC: GetItems_UC
) : ViewModel() {

    private val _dashboardState = MutableStateFlow(st_Dashboard())
    val dashboardState = _dashboardState.asStateFlow()

    fun initDashboard() {
        if (_dashboardState.value.isInitialized) return

        viewModelScope.launch {
            getBanner()
            getCategory()
            getItems()

            _dashboardState.update { it.copy(isInitialized = true) }
        }
    }


    fun onEvent(event: ev_dashboard) {
        when (event) {
            is ev_dashboard.setProductId -> _dashboardState.value =
                dashboardState.value.copy(setProductId = event.categoryId)

            ev_dashboard.InitDashboard -> initDashboard()
        }
    }

    private suspend fun getBanner() {
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

    private suspend fun getCategory() {

        getCategory_UC().collect { resources ->

            when (resources) {
                is Resources.Loading -> {
                    _dashboardState.update { it.copy(isLoading = true) }
                }

                is Resources.Success -> {

                    val categoryMapped = resources.data?.map { categories ->
                        CategoryDetails(
                            Pid = categories.Pid,
                            title = categories.title
                        )
                    } ?: emptyList()

                    _dashboardState.update {
                        it.copy(
                            categoryList = categoryMapped,
                            isLoading = false
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

    private suspend fun getItems() {
        getItems_UC().collect { resources ->
            when (resources) {
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

                    val mappeditems = resources.data?.map { mapped ->
                        ItemData(
                            imageUrl = mapped.picUrl,
                            price = mapped.price,
                            rating = mapped.rating,
                            title = mapped.title,
                            categoryId = mapped.categoryId
                        )
                    } ?: emptyList()

                    Log.e("viewModelGetItems", mappeditems.toString())

                    _dashboardState.update {
                        it.copy(
                            itemsState = mappeditems,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}



