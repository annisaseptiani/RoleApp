package com.example.roleapp.userpage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.roleapp.data.model.Photos
import com.example.roleapp.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavController, viewModel: UserViewModel) {
    val lazyItems : LazyPagingItems<Photos> = viewModel.pagingFlow.collectAsLazyPagingItems()

    val loadState = lazyItems.loadState

    Scaffold(
        topBar = {
                 TopAppBar(title = { Text(
                     text = "Contacts",
                     color = Color.White,
                     fontWeight = FontWeight.Bold,
                     style = MaterialTheme.typography.titleLarge,
                     textAlign = TextAlign.Center
                 ) })
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                ListPhotos(lazyItems = lazyItems, loadState = loadState)
            }

        }
    )

}

@Composable
fun ListPhotos(lazyItems : LazyPagingItems<Photos>, loadState: CombinedLoadStates){
    LazyColumn {
        items(lazyItems.itemCount) {
            index->
            val photos = lazyItems[index]
            photos?.let { PhotoItem(photosItem =it ) }
        }
        when {
            loadState.refresh is LoadState.Loading -> {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            loadState.append is LoadState.Loading -> {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            loadState.refresh is LoadState.Error -> {
                val e = lazyItems.loadState.refresh as LoadState.Error
                item {
                    BasicText(
                        text = "Refresh error: ${e.error.localizedMessage}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            loadState.append is LoadState.Error -> {
                val e = lazyItems.loadState.append as androidx.paging.LoadState.Error
                item {
                    BasicText(
                        text = "Append error: ${e.error.localizedMessage}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoItem(photosItem : Photos) {
    Card(shape = RoundedCornerShape(10), modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MainColor)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .padding(10.dp)
                .size(100.dp)
                .border(border = BorderStroke(1.dp, Color.White), shape = CircleShape)) {
                AsyncImage(model = photosItem.thumbnailUrl, contentDescription = "thumbnail")

            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = photosItem.id.toString(), style = MaterialTheme.typography.bodyMedium, color = Color.White)
                Text(text = photosItem.title, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                Text(
                    text = photosItem.url,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White, textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserPreview() {

}


