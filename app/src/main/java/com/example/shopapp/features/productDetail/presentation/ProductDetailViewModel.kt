package com.example.shopapp.features.productDetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.di.IoDispatcher
import com.example.shopapp.features.productDetail.domain.useCases.GetProductDetailsUC
import com.example.shopapp.features.productDetail.presentation.event.ProductDetailUiEvent
import com.example.shopapp.features.productDetail.presentation.state.ProductDetailState
import com.example.shopapp.features.productDetail.presentation.state.ProductDetailUiModel
import com.example.shopapp.ui.navigation.CATEGORY_PRODUCT_DETAIL_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val saveStateHandle: SavedStateHandle,
    private val getProductDetailsUC: GetProductDetailsUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel(){
    private val _productDetailState = MutableStateFlow(ProductDetailState())
    val productDetailState = _productDetailState.asStateFlow()

    private val _itemId : String = checkNotNull(saveStateHandle[CATEGORY_PRODUCT_DETAIL_ID_ARG]){
        "Detail Id should not be null"
    }

    init{
        _productDetailState.update {
            it.copy(
                itemId = _itemId
            )
        }
        fetchProductDetail(_itemId)
    }

    private fun fetchProductDetail(itemId: String) {
        viewModelScope.launch (dispatcher){
            _productDetailState.update { it.copy(isLoading = true, errorMsg = null) }

            getProductDetailsUC(itemId).collect{resources->
                _productDetailState.update {currentState->
                    when(resources){
                        is Resources.Loading -> currentState.copy(isLoading = true)
                        is Resources.Error -> currentState.copy(
                            errorMsg = resources.message ?: "An unexpected error occurred"
                        )
                        is Resources.Success -> currentState.copy(
                            isLoading = false,
                            items = resources.data?.map{ itemDomain->

                                ProductDetailUiModel(
                                    idItems = itemDomain.idItems,
                                    imageUrl = itemDomain.picUrl,
                                    price = itemDomain.price,
                                    rating = itemDomain.rating,
                                    title = itemDomain.title,
                                    description = itemDomain.description,
                                    categoryId = itemDomain.categoryId.toString(),
                                    showRecommended = itemDomain.showRecommended,
                                )

                            }?: emptyList()
                        )
                    }
                }
            }
        }
    }


    fun onEvent(event : ProductDetailUiEvent){

    }
}