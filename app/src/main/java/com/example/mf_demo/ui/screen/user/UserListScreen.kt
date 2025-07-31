package com.example.mf_demo.ui.screen.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mf_demo.R
import com.example.mf_demo.ui.components.CenterIndicator
import com.example.mf_demo.ui.components.RetryLoadDataBox
import com.example.mf_demo.ui.components.RetryLoadMoreDataBox
import com.example.mf_demo.ui.components.bar.SearchBar
import com.example.mf_demo.ui.components.bar.TopBar
import com.example.mf_demo.util.constant.CConstant
import com.example.mf_demo.viewModel.user.UserListViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel = viewModel(),
    onItemClick: (String) -> Unit,
) {
    val users = viewModel.users.collectAsLazyPagingItems()

    var query by remember { mutableStateOf("") }
    var debouncedQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            focusManager.clearFocus()
        }
    }
    LaunchedEffect(query) {
        delay(CConstant.TIME_USER_SEARCH_DELAY)
        debouncedQuery = query
    }

    val filteredUsers = remember(debouncedQuery, users.itemSnapshotList) {
        val currentUsers = users.itemSnapshotList
        if (debouncedQuery.isBlank()) currentUsers
        else currentUsers.filterNotNull().filter {
            it.login.contains(debouncedQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopBar(stringResource(R.string.header_user_list), onSettingClick = {
            })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                }

        ) {
            Column()
            {
                Surface(
                    color = colorResource(id = R.color.white),
                    tonalElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SearchBar(
                        modifier = Modifier.padding(top = 20.dp),
                        query = query,
                        onQueryChanged = { query = it },
                        onClear = { query = "" }
                    )
                }

                PullToRefreshBox(
                    isRefreshing = users.loadState.refresh is LoadState.Loading,
                    onRefresh = { users.refresh() },
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        // Initial loading state
                        users.loadState.refresh is LoadState.Loading -> {
                            //CenterIndicator() //use PullToRefreshBox's indicator
                        }
                        // Error state
                        users.loadState.refresh is LoadState.Error -> {
                            RetryLoadDataBox({ users.refresh() })
                        }

                        // Success state - show the list
                        else -> {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                items(filteredUsers.size) { index ->
                                    filteredUsers[index]?.let { user ->
                                        UserListItem(user, onClick = { onItemClick(user.login) })
                                    }
                                }

                                // Show loading indicator at the bottom when loading more items
                                if (users.loadState.append is LoadState.Loading) {
                                    item {
                                        CenterIndicator()
                                    }
                                }

                                // Show error state for append
                                if (users.loadState.append is LoadState.Error) {
                                    item {
                                        RetryLoadMoreDataBox { users.retry() }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

