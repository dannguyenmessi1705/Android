package com.didan.jetpack.compose.jetpackcourseapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.didan.jetpack.compose.jetpackcourseapp.R

@Composable
fun LatestLessonText(modifier: Modifier) {
    Text(
        text = "Latest Lessons",
        modifier = modifier,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun SeeAllText(modifier: Modifier) {
    Text(
        text = "See All",
        modifier = modifier,
        fontSize = 20.sp,
        color = Color.Blue
    )
}


@Composable
fun LessonCard(modifier: Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart) // Đặt nội dung ở góc trên bên trái
                .fillMaxHeight()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (lessonDate, timeImg, lessonSchedule, lessonImg, lessonTitle, lessonDesc) = createRefs()

                Text(
                    text = "Lesson Date",
                    color = Color.Gray,
                    modifier = Modifier
                        .constrainAs(lessonDate) { // Đặt vị trí của Text "Lesson Date" ở góc trên bên trái của Card bằng cách sử dụng ConstraintLayout và các ràng buộc (constraints) để xác định vị trí của nó.
                            top.linkTo(
                                parent.top,
                                16.dp
                            ) // Ràng buộc cạnh trên (top) của Text "Lesson Date" liên kết với cạnh trên của Card, đảm bảo rằng nó sẽ nằm ở góc trên bên trái của Card.
                            start.linkTo(
                                parent.start,
                                24.dp
                            ) // Ràng buộc cạnh trái (start) của Text "Lesson Date" liên kết với cạnh trái của Card, đảm bảo rằng nó sẽ nằm ở góc trên bên trái của Card.
                        }
                )

                Image(
                    painter = painterResource(R.drawable.time),
                    contentDescription = "Time",
                    modifier = Modifier
                        .constrainAs(timeImg) { // Đặt vị trí của Image "Time" ở góc dưới bên phải của Card bằng cách sử dụng ConstraintLayout và các ràng buộc (constraints) để xác định vị trí của nó.
                            top.linkTo(
                                lessonDate.bottom,
                                8.dp
                            ) // Ràng buộc cạnh trên (top) của Image "Time" liên kết với cạnh dưới (bottom) của Text "Lesson Date", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Text "Lesson Date" một khoảng cách nhất định.
                            start.linkTo(lessonDate.start) // Ràng buộc cạnh trái (start) của Image "Time" liên kết với cạnh trái của Text "Lesson Date", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Text "Lesson Date" một khoảng cách nhất định.
                        }
                )

                Text(
                    text = "Thur Jun 6 | 8:00 - 8:30 AM",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .constrainAs(lessonSchedule) { // Đặt vị trí của Text "Thur Jun 6 | 8:00 - 8:30 AM" ở góc dưới bên phải của Card bằng cách sử dụng ConstraintLayout và các ràng buộc (constraints) để xác định vị trí của nó.
                            top.linkTo(timeImg.top) // Ràng buộc cạnh trên (top) của Text "Thur Jun 6 | 8:00 - 8:30 AM" liên kết với cạnh trên (top) của Image "Time", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Image "Time" một khoảng cách nhất định.
                            start.linkTo(
                                timeImg.end,
                                4.dp
                            ) // Ràng buộc cạnh trái (start) của Text "Thur Jun 6 | 8:00 - 8:30 AM" liên kết với cạnh phải (end) của Image "Time", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Image "Time" một khoảng cách nhất định.
                        }
                )

                Image(
                    painter = painterResource(R.drawable.doctor),
                    contentDescription = "Doctor",
                    modifier = Modifier
                        .constrainAs(lessonImg) { // Đặt vị trí của Image "Doctor" ở góc dưới bên phải của Card bằng cách sử dụng ConstraintLayout và các ràng buộc (constraints) để xác định vị trí của nó.
                            top.linkTo(
                                timeImg.bottom,
                                16.dp
                            ) // Ràng buộc cạnh trên (top) của Image "Doctor" liên kết với cạnh dưới (bottom) của Image "Time", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Image "Time" một khoảng cách nhất định.
                            start.linkTo(timeImg.start) // Ràng buộc cạnh trái (start) của Image "Doctor" liên kết với cạnh trái của Image "Time", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Image "Time" một khoảng cách nhất định.
                            bottom.linkTo(
                                parent.bottom,
                                16.dp
                            ) // Ràng buộc cạnh dưới (bottom) của Image "Doctor" liên kết với cạnh dưới của Card, đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách cạnh dưới của Card một khoảng cách nhất định.
                        }
                        .size(60.dp)
                )

                Text(
                    text = "Doctor Consultation",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .constrainAs(lessonTitle) { // Đặt vị trí của Text "Doctor Consultation" ở góc dưới bên phải của Card bằng cách sử dụng ConstraintLayout và các ràng buộc (constraints) để xác định vị trí của nó.
                            top.linkTo(lessonImg.top) // Ràng buộc cạnh trên (top) của Text "Doctor Consultation" liên kết với cạnh trên (top) của Image "Doctor", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Image "Doctor" một khoảng cách nhất định.
                            start.linkTo(
                                lessonImg.end,
                                12.dp
                            ) // Ràng buộc cạnh trái (start) của Text "Doctor Consultation" liên kết với cạnh phải (end) của Image "Doctor", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Image "Doctor" một khoảng cách nhất định.
                        }
                )

                Text(
                    text = "Consult with our doctors to get the best advice for your health.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .constrainAs(lessonDesc) { // Đặt vị trí của Text "Consult with our doctors to get the best advice for your health." ở góc dưới bên phải của Card bằng cách sử dụng ConstraintLayout và các ràng buộc (constraints) để xác định vị trí của nó.
                            top.linkTo(
                                lessonTitle.bottom,
                                2.dp
                            ) // Ràng buộc cạnh trên (top) của Text "Consult with our doctors to get the best advice for your health." liên kết với cạnh dưới (bottom) của Text "Doctor Consultation", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Text "Doctor Consultation" một khoảng cách nhất định.
                            start.linkTo(lessonTitle.start) // Ràng buộc cạnh trái (start) của Text "Consult with our doctors to get the best advice for your health." liên kết với cạnh trái của Text "Doctor Consultation", đảm bảo rằng nó sẽ nằm ở góc dưới bên phải của Card, cách Text "Doctor Consultation" một khoảng cách nhất định.
                        }
                        .padding(end = 4.dp)
                )
            }
        }
    }
}