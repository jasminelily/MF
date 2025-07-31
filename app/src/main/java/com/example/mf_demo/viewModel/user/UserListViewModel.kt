package com.example.mf_demo.viewModel.user

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.example.mf_demo.module.data.entity.User
import com.example.mf_demo.module.data.source.UserPagingSource
import com.example.mf_demo.viewModel.base.BasePagingViewModel
import kotlinx.coroutines.flow.Flow

class UserListViewModel() : BasePagingViewModel() {

    val users: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            prefetchDistance = 5
        ),
        pagingSourceFactory = {
            UserPagingSource(api)
        }
    ).flow.cachedIn(viewModelScope)

}
