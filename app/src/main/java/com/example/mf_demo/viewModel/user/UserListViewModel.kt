package com.example.mf_demo.viewModel.user

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.example.mf_demo.module.data.entity.User
import com.example.mf_demo.module.data.source.UserPagingSource
import com.example.mf_demo.util.constant.CConstant
import com.example.mf_demo.viewModel.base.BasePagingViewModel
import kotlinx.coroutines.flow.Flow

class UserListViewModel() : BasePagingViewModel() {

    val users: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = CConstant.PAGING_SIZE,
            enablePlaceholders = false,
            prefetchDistance = CConstant.PAGING_DISTANCE
        ),
        pagingSourceFactory = {
            UserPagingSource(api)
        }
    ).flow.cachedIn(viewModelScope)

}
