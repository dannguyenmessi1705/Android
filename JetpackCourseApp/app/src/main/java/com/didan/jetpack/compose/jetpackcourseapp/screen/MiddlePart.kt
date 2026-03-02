package com.didan.jetpack.compose.jetpackcourseapp.screen

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.didan.jetpack.compose.jetpackcourseapp.R

@Composable
fun WelcomeText(modifier: Modifier) {
    Text(
        text = "Welcome Back!",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = modifier

    )
}

@Composable
fun QuestionText(modifier: Modifier) {
    Text(
        text = "What do you want to learn?",
        color = Color.Black,
        modifier = modifier
    )
}

@Composable
fun JoinNowButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Text(
            text = "Join Now"
        )
    }
}

@Composable
fun CoursesImage(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.urgent_care),
        contentDescription = "Courses",
        modifier = modifier
    )
}