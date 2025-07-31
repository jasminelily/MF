package com.example.mf_demo.ui.screen.user

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mf_demo.viewModel.user.UserDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.SubcomposeAsyncImage
import com.example.mf_demo.R
import com.example.mf_demo.ui.components.NaviBar
import com.example.mf_demo.util.constant.CType
import com.example.mf_demo.viewModel.base.BaseRequestData

@Composable
fun UserDetailScreen(
    key: String,
    onRepoClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: UserDetailViewModel = viewModel()
) {
    LaunchedEffect(key) {
        viewModel.getData(BaseRequestData(CType.DataType.UserDetail.type, key))
    }

    val user = viewModel.userDetail
    val repos = viewModel.userRepos
    val isLoading = viewModel.isLoading

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
        return
    }

    Scaffold(
        topBar = {
            NaviBar(stringResource(R.string.header_user_detail), onBackClick = onBackClick)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            user?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SubcomposeAsyncImage(
                            model = it.avatarUrl,
                            loading = {
                                CircularProgressIndicator()
                            },
                            contentDescription = stringResource(R.string.desc_avatar),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(it.name, style = MaterialTheme.typography.titleLarge)
                            Text(
                                String.format(
                                    stringResource(R.string.ui_user_detail_lbl_account),
                                    it.login
                                )
                            )
                            Row {
                                Text(
                                    String.format(
                                        stringResource(R.string.ui_user_detail_lbl_follower),
                                        it.followers
                                    )
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    String.format(
                                        stringResource(R.string.ui_user_detail_lbl_following),
                                        it.following
                                    )
                                )

                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Text(
                        stringResource(R.string.ui_user_detail_lbl_repo),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(8.dp))

                    repos.filter { it -> !it.fork }.forEach { repo ->
                        UserDetailItem(repo, onClick = {
                            viewModel.selectedRepo = repo
                            viewModel.isShowDialog = true
                        })
                    }

                }
            }
        }
    }

    viewModel.ShowDialog(CType.MessageType.OpenUrl) {
        viewModel.selectedRepo?.htmlUrl?.let { url ->
            onRepoClick(url)
        }
    }
}

