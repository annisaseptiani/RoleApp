package com.example.roleapp.userpage

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.roleapp.data.model.Photos
import com.example.roleapp.ui.theme.CustomButton
import com.example.roleapp.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavHostController, viewModel: UserViewModel) {
    val lazyItems : LazyPagingItems<Photos> = viewModel.pagingFlow.collectAsLazyPagingItems()

    val loadState = lazyItems.loadState

    Scaffold(
        topBar = {
                 TopAppBar(title = { Text(
                     text = "User",
                     color = MainColor,
                     fontWeight = FontWeight.Bold,
                     style = MaterialTheme.typography.titleLarge,
                     textAlign = TextAlign.Center
                 ) })
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                    horizontalArrangement = Arrangement.End) {
                    CustomButton(text = "Logout") {
                        viewModel.logout(navController)
                    }
                }
                ListPhotos(lazyItems = lazyItems, loadState = loadState)
            }

        }
    )

}

@Composable
fun ListPhotos(lazyItems : LazyPagingItems<Photos>, loadState: CombinedLoadStates){
    LazyColumn {
        items(lazyItems.itemCount) { index ->
            val photos = lazyItems[index]
            photos?.let { PhotoItem(photosItem = it) }
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
    val context= LocalContext.current
    Card(shape = RoundedCornerShape(10), modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, MainColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(modifier = Modifier
                    .padding(10.dp)
                    .size(100.dp),
                    model = photosItem.thumbnailUrl, contentDescription = "thumbnail")
             Column(modifier = Modifier.padding(10.dp)) {
                Text(text = photosItem.id.toString(), style = MaterialTheme.typography.bodyMedium, color = MainColor)
                Text(text = photosItem.title, style = MaterialTheme.typography.bodyMedium, color = MainColor)
                 val annotatedString = remember {
                     buildAnnotatedString {
                         append(photosItem.url)
                         addStyle(
                             style = SpanStyle(
                                 color = Color.Blue,
                                 textDecoration = TextDecoration.Underline
                             ),
                             start = 0,
                             end = photosItem.url.length
                         )

                         // Add a clickable annotation
                         addStringAnnotation(
                             tag = "URL",
                             annotation = photosItem.url,
                             start = 0,
                             end = photosItem.url.length
                         )
                     }
                 }

                 ClickableText(
                     text = annotatedString,
                     style = MaterialTheme.typography.bodyMedium,
                     onClick = { offset ->
                         annotatedString.getStringAnnotations("URL", offset, offset)
                             .firstOrNull()?.let { annotation ->
                                 val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                                 context.startActivity(intent)
                             }
                     }
                 )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserPreview() {

}


