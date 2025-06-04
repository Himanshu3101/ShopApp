package com.example.shopapp.features.productList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.di.IoDispatcher
import com.example.shopapp.features.productList.presentation.event.ProductListUiEvent
import com.example.shopapp.features.productList.presentation.state.ProductListState
import com.example.shopapp.features.productList.domain.useCases.GetItemForCategoryUC
import com.example.shopapp.features.productList.presentation.state.ProductListUiModel
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
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getItemForCategoryUC: GetItemForCategoryUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(){

    private val _productListState = MutableStateFlow(ProductListState())
    val productListState = _productListState.asStateFlow()

    private val _categoryId : String = checkNotNull(savedStateHandle[CATEGORY_ID_ARG]){
        "Category ID should not be null"
    }

    // NEW: Sealed class for navigation events from ProductListViewModel
    sealed class NavigationEvent {
//        data class ToProductDetail(val productId: String) : NavigationEvent()
    }
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()


    init {
        // Update the initial state with category ID/title if available from args
       /* _productListState.update {
            it.copy(
                categoryTitle = savedStateHandle[CATEGORY_ID_ARG] ?: "Products", //Fallback or retrieve actual title if passed
                categoryId = _categoryId
            )
        }*/
        fetchProductsByCategoryId(_categoryId)
    }

    private fun fetchProductsByCategoryId(id: String) {

        viewModelScope.launch (dispatcher){
            _productListState.update { it.copy(isLoading = true, errorMsg = null) }

            getItemForCategoryUC(id).collect { resources ->
                _productListState.update { currentState ->
                    when (resources) {
                        is Resources.Loading -> currentState.copy(isLoading = true)
                        is Resources.Success -> currentState.copy(
                            isLoading = false,
                            items = resources.data?.map { itemDomain ->

                                ProductListUiModel(
                                    imageUrl = itemDomain.picUrl,
                                    price = itemDomain.price,
                                    rating = itemDomain.rating,
                                    title = itemDomain.title,
                                    categoryId = itemDomain.categoryId.toString(),
                                    showRecommended = itemDomain.showRecommended,
                                )

                            } ?: emptyList(),
                        )
                        is Resources.Error -> currentState.copy(
                            errorMsg = resources.message ?: "An unexpected error occurred",
                        )
                    }
                }
            }
        }

    }

    fun onEvent(event : ProductListUiEvent){
        when(event){
            is ProductListUiEvent.InitProductList -> {}
//            is ProductListUiEvent.SetCategoryId -> {}
        }
    }
}