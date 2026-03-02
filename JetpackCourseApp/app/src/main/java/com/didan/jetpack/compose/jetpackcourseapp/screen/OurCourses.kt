package com.didan.jetpack.compose.jetpackcourseapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.didan.jetpack.compose.jetpackcourseapp.R

@Composable
fun TextOurCourses(modifier: Modifier) {
    Text(
        text = "New Courses",
        modifier = modifier,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun CourseOne(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.consultation_icon),
        contentDescription = "Course One",
        modifier = modifier
            .size(70.dp)
    )
}

@Composable
fun CourseTwo(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.ambulance_icon),
        contentDescription = "Course Two",
        modifier = modifier
            .size(70.dp)
    )
}

@Composable
fun CourseThree(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.medicines_icon),
        contentDescription = "Course Three",
        modifier = modifier
            .size(70.dp)
    )
}

@Composable
fun OneText(modifier: Modifier) {
    Text(
        text = "Consultation",
        modifier = modifier,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = Color.Black
    )
}

@Composable
fun TwoText(modifier: Modifier) {
    Text(
        text = "Ambulance",
        modifier = modifier,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = Color.Black
    )
}

@Composable
fun ThreeText(modifier: Modifier) {
    Text(
        text = "Medicines",
        modifier = modifier,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = Color.Black
    )
}