package com.didan.jetpack.compose.jetpackcourseapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintLayoutScreen() {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface) // Background surface có nghĩa là màu nền của ứng dụng, thường được sử dụng để tạo ra một lớp nền cho các thành phần khác trong giao diện người dùng. Nó giúp tạo ra sự tương phản và làm nổi bật các thành phần khác trên giao diện.
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Bật cuộn dọc cho ConstraintLayout, cho phép người dùng cuộn nội dung nếu nó vượt quá kích thước của màn hình, rememberScrollState() được sử dụng để lưu trữ trạng thái cuộn, giúp duy trì vị trí cuộn khi người dùng tương tác với giao diện.
    ) {
        // 1. Tạo một reference cho Composable, nó sẽ được sử dụng để định vị và liên kết các thành phần khác trong ConstraintLayout. Reference này giống như một "điểm tham chiếu" mà bạn có thể sử dụng để xác định vị trí của các thành phần khác dựa trên nó.
        val (gradientBackground,
            profileImg, notificationImg,
            welcomeText, questionText, joinNowButton, courses,
            card,
            newCourses,
            courseOneImg, courseTwoImg, courseThreeImg,
            oneText, twoText, threeText
        ) = createRefs()

        val (latestLessonText, seeAllText,
            lessonCard
        ) = createRefs()

        // GuideLine
        val horizontalGuideline1 =
            createGuidelineFromTop(0.45f) // Tạo một guideline ngang cách cạnh trên của ConstraintLayout 45% chiều cao của nó. Guideline này sẽ giúp định vị các thành phần khác dựa trên tỷ lệ phần trăm của chiều cao của ConstraintLayout.

        BackgroundGradient(
            modifier = Modifier
                // 2. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, gradientBackground sẽ được định vị dựa trên reference gradientBackground
                .constrainAs(gradientBackground) {
                    top.linkTo(parent.top) // Liên kết cạnh trên của Composable với cạnh trên của parent (ConstraintLayout)
                    end.linkTo(parent.end) // Liên kết cạnh phải của Composable với cạnh phải của parent (ConstraintLayout)
                    start.linkTo(parent.start) // Liên kết cạnh trái của Composable với cạnh trái
                    bottom.linkTo(horizontalGuideline1) // Liên kết cạnh dưới của Composable với guideline ngang đã tạo trước đó, điều này sẽ giúp Composable được đặt ở vị trí cách cạnh trên của ConstraintLayout 45% chiều cao của nó.
                    width =
                        Dimension.fillToConstraints // Chiều rộng của Composable sẽ được mở rộng để lấp đầy khoảng cách giữa các ràng buộc đã thiết lập (start và end), điều này đảm bảo rằng Composable sẽ chiếm toàn bộ chiều rộng của ConstraintLayout, trừ khi có các ràng buộc khác giới hạn nó.
                    height =
                        Dimension.fillToConstraints // Chiều cao của Composable sẽ được mở rộng để lấp đầy khoảng cách giữa các ràng buộc đã thiết lập (top và bottom), điều này đảm bảo rằng Composable sẽ chiếm toàn bộ chiều cao của khoảng cách giữa cạnh trên của ConstraintLayout và guideline ngang, trừ khi có các ràng buộc khác giới hạn nó.
                })

        // 2. Header (Chain)
        val topGuideline =
            createGuidelineFromTop(38.dp) // Tạo một guideline ngang cách cạnh trên của ConstraintLayout 16dp, điều này sẽ giúp định vị các thành phần khác dựa trên khoảng cách cố định từ cạnh trên của ConstraintLayout.
        val startGuideline =
            createGuidelineFromStart(16.dp) // Tạo một guideline dọc cách cạnh trái của ConstraintLayout 16dp, điều này sẽ giúp định vị các thành phần khác dựa trên khoảng cách cố định từ cạnh trái của ConstraintLayout.
        val endGuideline =
            createGuidelineFromEnd(16.dp) // Tạo một guideline dọc cách cạnh phải của ConstraintLayout 16dp, điều này sẽ giúp định vị các thành phần khác dựa trên khoảng cách cố định từ cạnh phải của ConstraintLayout.

        // chain
        createHorizontalChain(
            profileImg,
            notificationImg, // Tạo một chuỗi ngang bao gồm profileImg và notificationImg, điều này sẽ giúp định vị hai thành phần này theo một cách có tổ chức và đồng đều trên giao diện người dùng.
            chainStyle = ChainStyle.SpreadInside // ChainStyle.SpreadInside có nghĩa là các thành phần trong chuỗi sẽ được phân bố đều nhau giữa các ràng buộc đã thiết lập (start và end), nhưng sẽ không chiếm toàn bộ khoảng cách giữa chúng. Điều này tạo ra một khoảng cách đều đặn giữa các thành phần trong chuỗi, nhưng không làm cho chúng chiếm toàn bộ chiều rộng của ConstraintLayout.
        )

        ProfileImage(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, profileImg sẽ được định vị dựa trên reference profileImg
                .constrainAs(profileImg) {
                    top.linkTo(topGuideline) // Liên kết cạnh trên của Composable với guideline ngang đã tạo trước đó, điều này sẽ giúp Composable được đặt ở vị trí cách cạnh trên của ConstraintLayout 16dp.
                })

        NotificationImg(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, notificationImg sẽ được định vị dựa trên reference notificationImg
                .constrainAs(notificationImg) {
                    top.linkTo(profileImg.top) // Liên kết cạnh trên của Composable với cạnh trên của profileImg, điều này sẽ giúp notificationImg được đặt ở cùng vị trí ngang với profileImg, tạo ra sự đồng nhất trong giao diện người dùng.
                    bottom.linkTo(profileImg.bottom) // Liên kết cạnh dưới của Composable với cạnh dưới của profileImg, điều này sẽ giúp notificationImg được đặt ở cùng vị trí dọc với profileImg, tạo ra sự đồng nhất trong giao diện người dùng.
                }
        )

        // 3. Middle part
        WelcomeText(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, welcomeText sẽ được định vị dựa trên reference welcomeText
                .constrainAs(welcomeText) {
                    top.linkTo(
                        profileImg.bottom,
                        margin = 24.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của profileImg, với một khoảng cách (margin) là 32dp, điều này sẽ giúp welcomeText được đặt ở vị trí cách profileImg 32dp về phía dưới.
                    start.linkTo(startGuideline) // Liên kết cạnh trái của Composable với guideline dọc đã tạo trước đó, điều này sẽ giúp welcomeText được đặt ở vị trí cách cạnh trái của ConstraintLayout 16dp.
                }
        )

        QuestionText(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, questionText sẽ được định vị dựa trên reference questionText
                .constrainAs(questionText) {
                    top.linkTo(welcomeText.bottom) // Liên kết cạnh trên của Composable với cạnh dưới của welcomeText, điều này sẽ giúp questionText được đặt ở vị trí ngay dưới welcomeText, tạo ra một sự liên kết rõ ràng giữa hai thành phần này.
                    start.linkTo(welcomeText.start) // Liên kết cạnh trái của Composable với cạnh trái của welcomeText, điều này sẽ giúp questionText được căn chỉnh theo cùng một đường thẳng đứng với welcomeText, tạo ra sự đồng nhất trong giao diện người dùng.
                }
        )

        JoinNowButton(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, joinNowButton sẽ được định vị dựa trên reference joinNowButton
                .constrainAs(joinNowButton) {
                    top.linkTo(
                        questionText.bottom,
                        margin = 32.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của questionText, với một khoảng cách (margin) là 32dp, điều này sẽ giúp joinNowButton được đặt ở vị trí cách questionText 32dp về phía dưới.
                    start.linkTo(questionText.start) // Liên kết cạnh trái của Composable với cạnh trái của questionText, điều này sẽ giúp joinNowButton được căn chỉnh theo cùng một đường thẳng đứng với questionText, tạo ra sự đồng nhất trong giao diện người dùng.
                    end.linkTo(questionText.end) // Liên kết cạnh phải của Composable với cạnh phải của questionText, điều này sẽ giúp joinNowButton được căn chỉnh theo cùng một đường thẳng đứng với questionText, tạo ra sự đồng nhất trong giao diện người dùng.
                }
        ) {}

        CoursesImage(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, courses sẽ được định vị dựa trên reference courses
                .constrainAs(courses) {
                    bottom.linkTo(
                        horizontalGuideline1,
                        margin = 32.dp
                    ) // Liên kết cạnh dưới của Composable với guideline ngang đã tạo trước đó, với một khoảng cách (margin) là 16dp, điều này sẽ giúp courses được đặt ở vị trí cách guideline ngang 16dp về phía trên, tạo ra sự cân đối trong giao diện người dùng.
                    end.linkTo(endGuideline) // Liên kết cạnh phải của Composable với guideline dọc đã tạo trước đó, điều này sẽ giúp courses được đặt ở vị trí cách cạnh phải của ConstraintLayout 16dp.
                    top.linkTo(
                        joinNowButton.bottom,
                        margin = 0.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của joinNowButton, với một khoảng cách (margin) là 16dp, điều này sẽ giúp courses được đặt ở vị trí cách joinNowButton 16dp về phía dưới, tạo ra sự liên kết rõ ràng giữa hai thành phần này.
                    width =
                        Dimension.value(240.dp) // Đặt chiều rộng của Composable thành một giá trị cố định là 260dp, điều này sẽ đảm bảo rằng courses sẽ có một kích thước cụ thể về chiều rộng, tạo ra sự nhất quán trong giao diện người dùng. Tuy nhiên, nếu bạn muốn courses có thể mở rộng hoặc thu nhỏ dựa trên không gian có sẵn, bạn có thể sử dụng Dimension.fillToConstraints thay vì Dimension.value(260.dp).
                    height =
                        Dimension.fillToConstraints // Cần phải đặt height là fillToConstraints để đảm bảo rằng Composable sẽ mở rộng để lấp đầy khoảng cách giữa các ràng buộc đã thiết lập (top và bottom), điều này giúp đảm bảo rằng courses sẽ chiếm toàn bộ chiều cao của khoảng cách giữa guideline ngang và cạnh dưới của ConstraintLayout, tạo ra sự cân đối trong giao diện người dùng.
                }
        )

        // 4. The Card
        MyCard(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, card sẽ được định vị dựa trên reference card
                .constrainAs(card) {
                    top.linkTo(
                        horizontalGuideline1,
                        margin = (-32).dp
                    ) // Liên kết cạnh trên của Composable với guideline ngang đã tạo trước đó, với một khoảng cách âm (margin) là -4dp, điều này sẽ giúp card được đặt ở vị trí cách guideline ngang 4dp về phía trên, tạo ra sự chồng chéo nhẹ giữa card và guideline ngang, tạo ra một hiệu ứng thị giác thú vị trong giao diện người dùng.
                    start.linkTo(parent.start) // Liên kết cạnh trái của Composable với cạnh trái của parent (ConstraintLayout), điều này sẽ giúp card được đặt ở vị trí cách cạnh trái của ConstraintLayout 0dp, tạo ra sự liên kết rõ ràng giữa card và cạnh trái của ConstraintLayout.
                    end.linkTo(parent.end) // Liên kết cạnh phải của Composable với cạnh phải của parent (ConstraintLayout), điều này sẽ giúp card được đặt ở vị trí cách cạnh phải của ConstraintLayout 0dp, tạo ra sự liên kết rõ ràng giữa card và cạnh phải của ConstraintLayout.
                    bottom.linkTo(parent.bottom) // Liên kết cạnh dưới của Composable với cạnh dưới của parent (ConstraintLayout), điều này sẽ giúp card được đặt ở vị trí cách cạnh dưới của ConstraintLayout 0dp, tạo ra sự liên kết rõ ràng giữa card và cạnh dưới của ConstraintLayout.
                    width =
                        Dimension.fillToConstraints // Chiều rộng của Composable sẽ được mở rộng để lấp đầy khoảng cách giữa các ràng buộc đã thiết lập (start và end), điều này đảm bảo rằng card sẽ chiếm toàn bộ chiều rộng của ConstraintLayout, trừ khi có các ràng buộc khác giới hạn nó.
                    height =
                        Dimension.fillToConstraints // Chiều cao của Composable sẽ được mở rộng để lấp đầy khoảng cách giữa các ràng buộc đã thiết lập (top và bottom), điều này đảm bảo rằng card sẽ chiếm toàn bộ chiều cao của ConstraintLayout, trừ khi có các ràng buộc khác giới hạn nó.

                }
        )

        // Our Courses Section
        TextOurCourses(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, newCourses sẽ được định vị dựa trên reference newCourses
                .constrainAs(newCourses) {
                    top.linkTo(
                        card.top,
                        margin = 24.dp
                    ) // Liên kết cạnh trên của Composable với cạnh trên của card, với một khoảng cách (margin) là 24dp, điều này sẽ giúp newCourses được đặt ở vị trí cách card 24dp về phía dưới, tạo ra sự liên kết rõ ràng giữa newCourses và card.
                    start.linkTo(
                        card.start,
                        margin = 16.dp
                    ) // Liên kết cạnh trái của Composable với cạnh trái của card, với một khoảng cách (margin) là 16dp, điều này sẽ giúp newCourses được đặt ở vị trí cách cạnh trái của card 16dp, tạo ra sự liên kết rõ ràng giữa newCourses và card.
                }
        )

        CourseOne(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, courseOneImg sẽ được định vị dựa trên reference courseOneImg
                .constrainAs(courseOneImg) {
                    top.linkTo(
                        newCourses.bottom,
                        margin = 16.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của newCourses, với một khoảng cách (margin) là 16dp, điều này sẽ giúp courseOneImg được đặt ở vị trí cách newCourses 16dp về phía dưới, tạo ra sự liên kết rõ ràng giữa courseOneImg và newCourses.
                }
        )

        CourseTwo(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, courseTwoImg sẽ được định vị dựa trên reference courseTwoImg
                .constrainAs(courseTwoImg) {
                    top.linkTo(courseOneImg.top) // Liên kết cạnh trên của Composable với cạnh dưới của courseOneImg, điều này sẽ giúp courseTwoImg được đặt ở vị trí ngay dưới course
                    bottom.linkTo(courseOneImg.bottom) // Liên kết cạnh dưới của Composable với cạnh dưới của courseOneImg, điều này sẽ giúp courseTwoImg được đặt ở cùng vị trí dọc với courseOneImg, tạo ra sự đồng nhất trong giao diện người dùng.
                }
        )

        CourseThree(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, courseThreeImg sẽ được định vị dựa trên reference courseThreeImg
                .constrainAs(courseThreeImg) {
                    top.linkTo(courseTwoImg.top) // Liên kết cạnh trên của Composable với cạnh dưới của courseTwoImg, điều này sẽ giúp courseThreeImg được đặt ở vị trí ngay dưới courseTwoImg, tạo ra sự liên kết rõ ràng giữa courseThreeImg và courseTwoImg.
                    bottom.linkTo(courseTwoImg.bottom) // Liên kết cạnh dưới của Composable với cạnh dưới của courseTwoImg, điều này sẽ giúp courseThreeImg được đặt ở cùng vị trí dọc với courseTwoImg, tạo ra sự đồng nhất trong giao diện người dùng.
                }
        )

        // Chain cho 3 courses
        createHorizontalChain(
            courseOneImg,
            courseTwoImg,
            courseThreeImg, // Tạo một chuỗi ngang bao gồm courseOneImg, courseTwoImg và courseThreeImg, điều này sẽ giúp định vị ba thành phần này theo một cách có tổ chức và đồng đều trên giao diện người dùng.
            chainStyle = ChainStyle.Spread
        )

        OneText(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, oneText sẽ được định vị dựa trên reference oneText
                .constrainAs(oneText) {
                    top.linkTo(
                        courseOneImg.bottom,
                        margin = 12.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của courseOneImg, với một khoảng cách (margin) là 12dp, điều này sẽ giúp oneText được đặt ở vị trí cách courseOneImg 12dp về phía dưới, tạo ra sự liên kết rõ ràng giữa oneText và courseOneImg.
                    start.linkTo(courseOneImg.start) // Liên kết cạnh trái của Composable với cạnh trái của courseOneImg, điều này sẽ giúp oneText được căn chỉnh theo cùng một đường thẳng đứng với courseOneImg, tạo ra sự đồng nhất trong giao diện người dùng.
                    end.linkTo(courseOneImg.end) // Liên kết cạnh phải của Composable với cạnh phải của courseOneImg, điều này sẽ giúp oneText được căn chỉnh theo cùng một đường thẳng đứng với courseOneImg, tạo ra sự đồng nhất trong giao diện người dùng.
                }
        )

        TwoText(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, twoText sẽ được định vị dựa trên reference twoText
                .constrainAs(twoText) {
                    top.linkTo(
                        courseTwoImg.bottom,
                        margin = 12.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của courseTwoImg, với một khoảng cách (margin) là 12dp, điều này sẽ giúp twoText được đặt ở vị trí cách courseTwoImg 12dp về phía dưới, tạo ra sự liên kết rõ ràng giữa twoText và courseTwoImg.
                    start.linkTo(courseTwoImg.start) // Liên kết cạnh trái của Composable với cạnh trái của courseTwoImg, điều này sẽ giúp twoText được căn chỉnh theo cùng một đường thẳng đứng với courseTwoImg, tạo ra sự đồng nhất trong giao diện người dùng.
                    end.linkTo(courseTwoImg.end) // Liên kết cạnh phải của Composable với cạnh phải của courseTwoImg, điều này sẽ giúp twoText được căn chỉnh theo cùng một đường thẳng đứng với courseTwoImg, tạo ra sự đồng nhất trong giao diện người dùng.
                }
        )

        ThreeText(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, threeText sẽ được định vị dựa trên reference threeText
                .constrainAs(threeText) {
                    top.linkTo(
                        courseThreeImg.bottom,
                        margin = 12.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của courseThreeImg, với một khoảng cách (margin) là 12dp, điều này sẽ giúp threeText được đặt ở vị trí cách courseThreeImg 12dp về phía dưới, tạo ra sự liên kết rõ ràng giữa threeText và courseThreeImg.
                    start.linkTo(courseThreeImg.start) // Liên kết cạnh trái của Composable với cạnh trái của courseThreeImg, điều này sẽ giúp threeText được căn chỉnh theo cùng một đường thẳng đứng với courseThreeImg, tạo ra sự đồng nhất trong giao diện người dùng.
                    end.linkTo(courseThreeImg.end) // Liên kết cạnh phải của Composable với cạnh phải của courseThreeImg, điều này sẽ giúp threeText được căn chỉnh theo cùng một đường thẳng đứng với courseThreeImg, tạo ra sự đồng nhất trong giao diện người dùng.
                }
        )

        // Latest Lesson Part
        LatestLessonText(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, latestLessonText sẽ được định vị dựa trên reference latestLessonText
                .constrainAs(latestLessonText) {
                    top.linkTo(
                        oneText.bottom,
                        margin = 30.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của oneText, với một khoảng cách (margin) là 30dp, điều này sẽ giúp latestLessonText được đặt ở vị trí cách oneText 30dp về phía dưới, tạo ra sự liên kết rõ ràng giữa latestLessonText và oneText.
                    start.linkTo(startGuideline) // Liên kết cạnh trái của Composable với guideline dọc đã tạo trước đó, điều này sẽ giúp latestLessonText được đặt ở vị trí cách cạnh trái của ConstraintLayout 16dp, tạo ra sự liên kết rõ ràng giữa latestLessonText và guideline dọc.
                }
        )

        SeeAllText(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, seeAllText sẽ được định vị dựa trên reference seeAllText
                .constrainAs(seeAllText) {
                    top.linkTo(
                        latestLessonText.top
                    ) // Liên kết cạnh trên của Composable với cạnh trên của latestLessonText, điều này sẽ giúp seeAllText được đặt ở cùng vị trí ngang với latestLessonText, tạo ra sự đồng nhất trong giao diện người dùng.
                    end.linkTo(endGuideline) // Liên kết cạnh phải của Composable với guideline dọc đã tạo trước đó, điều này sẽ giúp seeAllText được đặt ở vị trí cách cạnh phải của ConstraintLayout 16dp, tạo ra sự liên kết rõ ràng giữa seeAllText và guideline dọc.
                }
        )

        LessonCard(
            modifier = Modifier
                // 3. Sử dụng constrainAs để định vị Composable dựa trên reference đã tạo. Trong trường hợp này, lessonCard sẽ được định vị dựa trên reference lessonCard
                .constrainAs(lessonCard) {
                    top.linkTo(
                        latestLessonText.bottom,
                        margin = 16.dp
                    ) // Liên kết cạnh trên của Composable với cạnh dưới của newCourses, với một khoảng cách (margin) là 16dp, điều này sẽ giúp lessonCard được đặt ở vị trí cách newCourses 16dp về phía dưới, tạo ra sự liên kết rõ ràng giữa lessonCard và newCourses.
                    start.linkTo(latestLessonText.start) // Liên kết cạnh trái của Composable với cạnh trái của newCourses, điều này sẽ giúp lessonCard được căn chỉnh theo cùng một đường thẳng đứng với newCourses, tạo ra sự đồng nhất trong giao diện người dùng.
                    end.linkTo(endGuideline) // Liên kết cạnh phải của Composable với guideline dọc đã tạo trước đó, điều này sẽ giúp lessonCard được đặt ở vị trí cách cạnh phải của ConstraintLayout 16dp, tạo ra sự liên kết rõ ràng giữa lessonCard và guideline dọc.
                    width =
                        Dimension.fillToConstraints // Chiều rộng của Composable sẽ được mở rộng để lấp đầy khoảng cách giữa các ràng buộc đã thiết lập (start và end), điều này đảm bảo rằng lessonCard sẽ chiếm toàn bộ chiều rộng của khoảng cách giữa guideline dọc và cạnh trái của ConstraintLayout, tạo ra sự cân đối trong giao diện người dùng.
                    height =
                        Dimension.wrapContent // Chiều cao của Composable sẽ được điều chỉnh để vừa với nội dung bên trong nó, điều này đảm bảo rằng lessonCard sẽ có chiều cao phù hợp với nội dung mà nó chứa, tạo ra sự linh hoạt trong giao diện người dùng.
                }
        )

    }
}
