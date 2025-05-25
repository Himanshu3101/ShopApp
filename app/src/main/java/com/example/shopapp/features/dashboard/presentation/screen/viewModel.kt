package com.example.shopapp.features.dashboard.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.network.Resources
import com.example.shopapp.core.util.retryWithExponentialBackoff
import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetCategory_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetItems_UC
import com.example.shopapp.features.dashboard.presentation.screen.event.Ev_dashboard
import com.example.shopapp.features.dashboard.presentation.screen.state.CategoryDetails
import com.example.shopapp.features.dashboard.presentation.screen.state.ItemData
import com.example.shopapp.features.dashboard.presentation.screen.state.st_Dashboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getBannerUC: GetBanner_UC,
    private val getCategoryUC: GetCategory_UC,
    private val getItemsUC: GetItems_UC
) : ViewModel() {

    private val _bannerState = MutableStateFlow<Resources<List<String>>>(Resources.Loading())
    private val _categoryState = MutableStateFlow<Resources<List<CategoryDetails>>>(Resources.Loading())
    private val _itemState = MutableStateFlow<Resources<List<ItemData>>>(Resources.Loading())


    private val _dashboardState = MutableStateFlow(st_Dashboard())
    val dashboardState = _dashboardState.asStateFlow()

    private fun initDashboard() {
        if (_dashboardState.value.isInitialized) return

        viewModelScope.launch {
            supervisorScope {
                launch { getBanner() }
                launch { getCategory() }
                launch { getItems() }
            }
        }

        viewModelScope.launch {
            combine(_bannerState, _categoryState, _itemState) { banners, categories, items ->
                val isLoading = listOf(banners, categories, items).any { it is Resources.Loading }

                st_Dashboard(
                    isInitialized = true,
                    isLoading = isLoading,
                    bannerUrls = (banners as? Resources.Success)?.data.orEmpty(),
                    categoryList = (categories as? Resources.Success)?.data.orEmpty(),
                    itemsState = (items as? Resources.Success)?.data.orEmpty()
                )
            }.distinctUntilChanged().collect {
                _dashboardState.value = it
            }
        }
    }


    fun onEvent(event: Ev_dashboard) {
        when (event) {
            is Ev_dashboard.SetProductId -> _dashboardState.value =
                dashboardState.value.copy(setProductId = event.categoryId)

            Ev_dashboard.InitDashboard -> initDashboard()
        }
    }

    private suspend fun getBanner() {
        try {
            getBannerUC()
                .retryWithExponentialBackoff()
                .collect { resources ->
                when (resources) {

                    is Resources.Success -> {
                        _bannerState.value = Resources.Success(resources.data?.map { it.url })
                    }

                    is Resources.Loading -> {
                        _bannerState.value = Resources.Loading()
                    }

                    is Resources.Error -> {
                        _bannerState.value = Resources.Error(resources.message)
                    }
                }
            }
        } catch (e: Exception) {
            _bannerState.value = Resources.Error("Exception : ${e.localizedMessage}")
        }


    }

    private suspend fun getCategory() {
        try {
            getCategoryUC()
                .retryWithExponentialBackoff()
                .collect { resources ->

                when (resources) {
                    is Resources.Loading -> {
                        _categoryState.value = Resources.Loading()
                    }

                    is Resources.Success -> {
                        _categoryState.value = Resources.Success(resources.data?.map { category ->
                            CategoryDetails(
                                Pid = category.Pid,
                                title = category.title
                            )
                        })
                    }

                    is Resources.Error -> {
                        _categoryState.value = Resources.Error(resources.message)
                    }
                }

            }
        } catch (e: Exception) {
            _categoryState.value = Resources.Error("Exception : ${e.localizedMessage}")
        }
    }

    private suspend fun getItems() {
        try {
            getItemsUC()
                .retryWithExponentialBackoff()
                .collect { resources ->
                when (resources) {
                    is Resources.Error -> {
                        _itemState.value = Resources.Error(resources.message)
                    }

                    is Resources.Loading -> {
                        _itemState.value = Resources.Loading()
                    }

                    is Resources.Success -> {

                        _itemState.value = Resources.Success(resources.data?.map { mapped ->
                            ItemData(
                                imageUrl = mapped.picUrl,
                                price = mapped.price,
                                rating = mapped.rating,
                                title = mapped.title,
                                categoryId = mapped.categoryId,
                                showRecommended = mapped.showRecommended
                            )
                        })
                    }
                }
            }
        } catch (e: Exception) {
            _itemState.value = Resources.Error("Exception : ${e.localizedMessage}")
        }
    }
}



