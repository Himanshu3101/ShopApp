package com.example.shopapp.features.dashboard.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.network.Resources
import com.example.shopapp.core.util.retryWithExponentialBackoff
import com.example.shopapp.features.dashboard.domain.remote.model.BannerDomain
import com.example.shopapp.features.dashboard.domain.remote.model.CategoryDomain
import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetCategory_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetItems_UC
import com.example.shopapp.features.dashboard.presentation.screen.event.DashboardUiEvent
import com.example.shopapp.features.dashboard.presentation.screen.state.CategoryDetails
import com.example.shopapp.features.dashboard.presentation.screen.state.ItemData
import com.example.shopapp.features.dashboard.presentation.screen.state.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getBannerUC: GetBanner_UC,
    private val getCategoryUC: GetCategory_UC,
    private val getItemsUC: GetItems_UC
) : ViewModel() {

    // Internal flows to hold raw Resources from Use Cases (private because all are combined)
    private val _bannerResources = MutableStateFlow<Resources<List<BannerDomain>>>(Resources.Loading())
    private val _categoryResources = MutableStateFlow<Resources<List<CategoryDomain>>>(Resources.Loading())
    private val _itemResources = MutableStateFlow<Resources<List<ItemDomain>>>(Resources.Loading())



    // Single source of truth for the UI state
    private val _dashboardState = MutableStateFlow(DashboardUiState())
    val dashboardState = _dashboardState.asStateFlow()

    fun onEvent(event: DashboardUiEvent) {
        when (event) {
            /*is DashboardUiEvent.SetProductId -> _dashboardState.value =
                dashboardState.value.copy(selectedProductId = event.categoryId)*/

            DashboardUiEvent.InitDashboard -> initDashboard()
        }
    }

    init {
        // Collect combined resources into the main UI state
        // This makes sure _dashboardUiState always reflects the latest combination
        combine(
            _bannerResources,
            _categoryResources,
            _itemResources
        ){ banners, categories, items ->

            // Determine overall loading state
            val isLoading = listOf(banners, categories, items).any { it is Resources.Loading }

            val errorMessage = (banners as? Resources.Error)?.message
                ?: (categories as? Resources.Error)?.message
                ?: (items as? Resources.Error)?.message

            // Map domain models to UI models
           val bannerUrls = (banners as? Resources.Success)?.data?.map { it.url }.orEmpty()
            val categoryList = (categories as? Resources.Success)?.data?.map { domainCategory ->
                CategoryDetails(
                    id = domainCategory.id,
                    title = domainCategory.title
                )
            }.orEmpty()
            val itemsState = (items as? Resources.Success)?.data?.map { domainItem ->
                ItemData(
                    imageUrl = domainItem.picUrl,
                    price = domainItem.price,
                    rating = domainItem.rating,
                    title = domainItem.title,
                    categoryId = domainItem.categoryId,
                    showRecommended = domainItem.showRecommended
                )
            }.orEmpty()


            DashboardUiState(
                isInitialized = !isLoading || errorMessage != null, // Once combine starts emitting, it's initialized
                isLoading = isLoading,
                bannerUrls = bannerUrls,
                categoryList = categoryList,
                itemsState = itemsState,
                errorMessage = errorMessage
            )
        }
        .distinctUntilChanged() // Only emit if the state actually changes
            .onEach { combinedState ->
                _dashboardState.value = combinedState  // Update the main UI state
            }
            .catch { e ->
                _dashboardState.value = _dashboardState.value.copy(
                    isLoading = false,
                    errorMessage = "An unexpected error occurred: ${e.localizedMessage}"
                )
            }
            .launchIn(viewModelScope) // Launch this collector in viewModelScope
    }

    private fun initDashboard() {
        // Only fetch if not already initialized OR if there was an error and we need to retry
        if (_dashboardState.value.isInitialized && _dashboardState.value.errorMessage == null) {
            return // Already successfully initialized
        }

        // Reset loading state if initiating a new load
        _dashboardState.value = _dashboardState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            supervisorScope {
                // Allows child coroutines to fail independently
                // Launch each data fetch concurrently
                launch {
                    getBannerUC()
                        .retryWithExponentialBackoff() // Apply retry logic
                        .onEach { _bannerResources.value = it } // Update internal flow
                        .catch { e -> _bannerResources.value = Resources.Error("Banner load error: ${e.localizedMessage}") } // Catch network exceptions here
                        .launchIn(this) // Launch in supervisorScope
                }
                launch {
                    getCategoryUC()
                        .retryWithExponentialBackoff()
                        .onEach { _categoryResources.value = it }
                        .catch { e -> _categoryResources.value = Resources.Error("Category load error: ${e.localizedMessage}") }
                        .launchIn(this)
                }
                launch {
                    getItemsUC()
                    .retryWithExponentialBackoff()
                    .onEach { _itemResources.value = it }
                    .catch { e -> _itemResources.value = Resources.Error("Items load error: ${e.localizedMessage}") }
                    .launchIn(this)
                }
            }
        }
    }
}



