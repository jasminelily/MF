package com.example.mf_demo.ui.screen.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    val allUsers by viewModel.users.collectAsState()

    var query by remember { mutableStateOf("") }
    var debouncedQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val isLoading = viewModel.isLoading


    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
        return
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            focusManager.clearFocus()
        }
    }
    LaunchedEffect(query) {
//        delay(CConstant.TIME_USER_SEARCH_DELAY)
        debouncedQuery = query
    }

    val filteredUsers = remember(debouncedQuery, allUsers) {
        if (debouncedQuery.isBlank()) allUsers
        else allUsers.filter {
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
                    isRefreshing = viewModel.isRefresh,
                    onRefresh = { viewModel.getData() },
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(filteredUsers) { user ->
                            UserListItem(user, onClick = { onItemClick(user.login) })
                        }
                    }
                }
            }
        }
    }

    viewModel.ShowRetry()
}
