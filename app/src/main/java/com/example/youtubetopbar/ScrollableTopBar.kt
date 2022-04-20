package com.example.youtubetopbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.youtubetopbar.viewmodel.ScrollViewModel

@Composable
fun ScrollableTopBar(
    viewModel: ScrollViewModel = hiltViewModel()
) {
    
    val scrollState = rememberLazyListState()
    
    val scrollUpState = viewModel.scrollUp.observeAsState()

    // 最初のやつが見えてないと、スクロールしても一緒なのか LiveData vs Stateでやっても結果は同じ
    viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 56.dp),
            state = scrollState
        ) {
            items(20) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(10.dp, 5.dp, 10.dp, 5.dp)
                        .background(Color.White),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(5.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img),
                                contentDescription = "Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(modifier = Modifier.padding(5.dp))

                            Column {
                                Text(
                                    text = "YouTubeDemo ${index + 1}",
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text(
                                    text = "Do you want to watch YouTube?? ${index + 1}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
        ScrollableAppBar(title = "YouTube", scrollUpState = scrollUpState)
    }
}

@Composable
fun ScrollableAppBar(
    title: String,
    background: Color = Color.White,
    scrollUpState: State<Boolean?>,
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    
    // animationってこれだけなのか
    val position by animateFloatAsState(
        if (scrollUpState.value == true) {
            -150f
        } else {
            0f
        }
    )
    
    // このUIの変動を、Modifierなどで呼び出すことで変更を加えることができるのね
    Surface(
        modifier = Modifier.graphicsLayer { 
            translationY = (position)
        },
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(background)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {

                if (navigationIcon != null) {
                    navigationIcon()
                }
                IconAndTitle(title = title)
            }
        }
    }
}

@Composable
fun IconAndTitle(
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 15.dp)
                .size(40.dp),
            painter = painterResource(id = R.drawable.img),
            contentDescription = null
        )

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = MaterialTheme.typography.body1.fontStyle
        )

        // 別デバイスで確認したときに、UIがずれたりしそう
        Spacer(modifier = Modifier.width(80.dp))

        TopBarIcons(iconId = R.drawable.ic_cast)
        TopBarIcons(iconId = R.drawable.ic_notifications)
        TopBarIcons(iconId = R.drawable.ic_search)

        Spacer(modifier = Modifier.width(8.dp))
        
        UserProfile()
    }
}

@Composable
fun TopBarIcons(
    iconId: Int,
    contentDescription: String? = null
) {
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = contentDescription
    )

    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun UserProfile() {
    
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .size(30.dp)
            .background(Color.Black.copy(alpha = 0.1f)),
        painter = painterResource(id = R.drawable.ic_person),
        contentDescription = null
    )
}