package com.example.shopapp.features.productList.domain.useCases

import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import com.example.shopapp.features.dashboard.domain.remote.repositoy.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetProductTypeUC @Inject constructor (
    private val itemRepository: ItemRepository
){

    operator fun invoke(categoryId:String) : Flow<Resources<List<ItemDomain>>> = flow{
        emit(Resources.Loading())

        try{
            val item = itemRepository.getItemsByCategoryId(categoryId)
            emit(Resources.Success(data = item))
        }catch(e:Exception){
            emit(Resources.Error(message = e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}