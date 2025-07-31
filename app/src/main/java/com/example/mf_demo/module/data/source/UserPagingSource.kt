package com.example.mf_demo.module.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mf_demo.module.api.base.ApiResult
import com.example.mf_demo.module.api.base.IApiRepository
import com.example.mf_demo.module.data.entity.User

class UserPagingSource(
    private val apiRepository: IApiRepository
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            when (val result = apiRepository.getUsers(page, pageSize)) {
                is ApiResult.Success -> {
                    val users = result.data
                    val nextKey = if (users.isEmpty()) null else page + 1
                    val prevKey = if (page == 1) null else page - 1

                    LoadResult.Page(
                        data = users,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                is ApiResult.Error -> {
                    LoadResult.Error(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}