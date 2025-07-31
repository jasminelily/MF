package com.example.mf_demo.ui.screen.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mf_demo.R
import com.example.mf_demo.ui.components.SearchBar
import com.example.mf_demo.ui.components.TopBar
import com.example.mf_demo.viewModel.user.UserListViewModel

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
//        delay(CConstant.TIME_USER_SEARCH_DELAY)
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
                ) {
                    when {
                        // Initial loading state
                        users.loadState.refresh is LoadState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        // Error state
                        users.loadState.refresh is LoadState.Error -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                // You can add a retry button here
                                viewModel.ShowRetry()
                            }
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
                                    filteredUsers.get(index)?.let { user ->
                                        UserListItem(user, onClick = { onItemClick(user.login) })
                                    }
                                }

                                // Show loading indicator at the bottom when loading more items
                                if (users.loadState.append is LoadState.Loading) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                // Show error state for append
                                if (users.loadState.append is LoadState.Error) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            // You can add a retry button here if needed
                                            CircularProgressIndicator()
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
}
