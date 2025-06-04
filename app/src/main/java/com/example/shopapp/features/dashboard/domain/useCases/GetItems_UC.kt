package com.example.shopapp.features.dashboard.domain.useCases

import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import com.example.shopapp.ui.common.interfaces.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetItems_UC(
    val itemRepository: ItemRepository
) {
    operator fun invoke() : Flow<Resources<List<ItemDomain>>> = flow {
        emit(Resources.Loading())
        try {
            emit(Resources.Success(data = itemRepository.getItems()))
        }catch (e:Exception){
            emit(Resources.Error(message = e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)
}