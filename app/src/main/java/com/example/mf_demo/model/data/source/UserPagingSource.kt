package com.example.mf_demo.model.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mf_demo.model.api.base.ApiResult
import com.example.mf_demo.model.api.base.IApiRepository
import com.example.mf_demo.model.data.entity.User

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
            //since start from 0
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val since = page * pageSize
            
            val rs = apiRepository.getUsers(since, pageSize)
            when (rs) {
                is ApiResult.Success -> {
                    val users = rs.data
                    val nextKey = if (users.size < pageSize) null else page + 1
                    val prevKey = if (page == 0) null else page - 1

                    LoadResult.Page(
                        data = users,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                is ApiResult.Error -> {
                    LoadResult.Error(Exception(rs.message))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}