package com.example.shopapp.featuresTest.dashboard

import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetCategory_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetItems_UC
import com.example.shopapp.features.dashboard.presentation.screen.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.mockito.Mock

@ExperimentalCoroutinesApi
class viewModelTest {

    @Mock
    private lateinit var getbannerUc: GetBanner_UC
    private lateinit var getCategoryUc: GetCategory_UC
    private lateinit var getItemsUc: GetItems_UC

    private lateinit var viewModel: DashboardViewModel


}