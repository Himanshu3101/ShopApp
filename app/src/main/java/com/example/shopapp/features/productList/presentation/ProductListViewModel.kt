package com.example.shopapp.features.productList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.di.IoDispatcher
import com.example.shopapp.features.productList.presentation.event.ProductListUiEvent
import com.example.shopapp.features.productList.presentation.state.ProductListState
import com.example.shopapp.features.productList.useCases.GetItemForCategory_UC
import com.example.shopapp.ui.navigation.CATEGORY_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getItemForCategoryUC: GetItemForCategory_UC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(){

    private val _productListState = MutableStateFlow(ProductListState())
    val productListState = _productListState.asStateFlow()

    private val _categoryId : String = checkNotNull(savedStateHandle[CATEGORY_ID_ARG]){
        "Category ID should not be null"
    }

    // NEW: Sealed class for navigation events from ProductListViewModel
    sealed class NavigationEvent {
        data class ToProductDetail(val productId: String) : NavigationEvent()
    }
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    init {
        // Update the initial state with category ID/title if available from args
        _productListState.update {
            it.copy(
                categoryTitle = savedStateHandle[CATEGORY_ID_ARG] ?: "Products", //Fallback or retrieve actual title if passed
                categoryId = _categoryId
            )
        }
        fetchProductsByCategoryId(_categoryId)
    }

    private fun fetchProductsByCategoryId(id: String) {
        viewModelScope.launch (dispatcher){
            _productListState.update { it.copy(isLoading = true, errorMsg = null) }
//TODO
            getItemsForCategoryUC(id).collect { resources ->
                _productListState.update { currentState ->
                    when (resources) {
                        is Resources.Loading -> currentState.copy(isLoading = true, errorMessage = null)
                        is Resources.Success -> currentState.copy(
                            isLoading = false,
                            items = resources.data?.map { itemDomain ->
                                ProductUiModel(
                                    id = itemDomain.Pid,
                                    name = itemDomain.name,
                                    price = itemDomain.price,
                                    imageUrl = itemDomain.imageUrl,
                                    description = itemDomain.description
                                )
                            } ?: emptyList(),
                            errorMessage = null
                        )
                        is Resources.Error -> currentState.copy(
                            isLoading = false,
                            errorMessage = resources.message
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event : ProductListUiEvent){
        when(event){
            is ProductListUiEvent.InitProductList -> {}
            is ProductListUiEvent.SetCategoryId -> {}
        }
    }
}