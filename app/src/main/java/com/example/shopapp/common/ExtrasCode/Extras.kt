package com.example.shopapp.common.ExtrasCode

/*
@Composable
fun CoilImageWithLoading(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(model = imageUrl)
    val state = painter.state

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        }

        if (state is AsyncImagePainter.State.Error) {
            Text("Error", color = Color.Red, fontSize = 12.sp)
        }
    }
}*/
