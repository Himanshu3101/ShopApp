package com.example.shopapp.features.productDetail.domain.useCases

import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import com.example.shopapp.features.dashboard.domain.remote.repositoy.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetProductDetailsUC @Inject constructor(
    private val itemRepository: ItemRepository
){
    operator fun invoke(itemId:String): Flow<Resources<List<ItemDomain>>> = flow{
        emit(Resources.Loading())
        try{
            val item = itemRepository.getProductDetails(itemId)
            emit(Resources.Success(data = item))
        }catch(e: Exception){
            emit(Resources.Error(message = e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}