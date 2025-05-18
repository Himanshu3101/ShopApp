package com.example.shopapp.features.dashboard.domain.useCases

import com.example.shopapp.core.util.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.Category
import com.example.shopapp.features.dashboard.domain.remote.repositoy.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCategory_UC(
    val categoryRepository: CategoryRepository
) {

    operator fun invoke() : Flow<Resources<List<Category>>> = flow {
        emit(Resources.Loading())

        try {
            emit(Resources.Success(data = categoryRepository.getCategory()))
        } catch (e: Exception) {
            emit(Resources.Error(message = e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}