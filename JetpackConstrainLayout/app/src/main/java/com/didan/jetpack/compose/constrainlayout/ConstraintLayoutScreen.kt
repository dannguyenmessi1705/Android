package com.didan.jetpack.compose.constrainlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ConstraintLayoutScreen() {
    // ConstraintLayout là một composable được sử dụng để tạo ra các bố cục phức tạp bằng cách định nghĩa các ràng buộc giữa các phần tử con. Nó cho phép bạn xác định vị trí và kích thước của các phần tử con dựa trên các ràng buộc mà bạn đặt ra, giúp tạo ra các giao diện linh hoạt và dễ dàng điều chỉnh.
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        // Các bước để sử dụng ConstraintLayout:
        // 1. Định nghĩa các tham chiếu (references) cho mỗi phần tử con mà bạn muốn định vị trong ConstraintLayout. Điều này giúp bạn có thể đặt ràng buộc giữa các phần tử một cách dễ dàng.
        // createRefs() được sử dụng để tạo ra các tham chiếu cho các phần tử con trong ConstraintLayout. Các tham chiếu này sẽ được sử dụng để đặt ràng buộc giữa các phần tử, chúng được sử dụng với `constrainAs` để xác định vị trí và kích thước của phần tử con dựa trên các ràng buộc mà bạn đặt ra.

        val (box1, box2, text) = createRefs() // Tạo ra ba tham chiếu cho hai hộp và một đoạn văn bản

        // GUIDELINES: Giúp tạo ra các bố cục nhất quán và thích ứng bằng cách định nghĩa các phần tử tương đối tỷ lệ % hoặc khoảng cách cụ thể từ đầu hoặc cuôi của phần tử cha.
        val guideLine1 =
            createGuidelineFromStart(0.05f) // Tạo một guideline (đường biên) cách 15% từ bên trái của phần tử cha (các phần tử con có thể liên kết với guideline này để định vị chính xác hơn)

        // BARRIERS: Được sử dụng để tạo ra các đường biên ràng buộc liên quan đến các cạnh của nhiều phần tử
        val barrier1 = createEndBarrier(
            box1,
            box2
        ) // Tạo một barrier (rào cản) ở phía cuối của box1 và box2, giúp đảm bảo rằng các phần tử khác không chồng lên chúng (Sẽ lấy vị trí cạnh cuối cùng của box1 nếu nó dài hơn box2, hoặc vị trí cạnh cuối cùng của box2 nếu nó dài hơn box1)

        // CHAINS: Cho phép bạn tạo ra các chuỗi ràng buộc giữa các phần tử, giúp phân phối không gian một cách linh hoạt giữa chúng.
        // ChainStyle: Có ba kiểu chuỗi chính:
        // Spread: Phân phối đều không gian giữa các phần tử trong chuỗi.
        // SpreadInside: Phân phối đều không gian giữa các phần tử, nhưng phần tử đầu tiên và cuối cùng sẽ được đặt ở cạnh của phần tử cha.
        // Packed: Các phần tử sẽ được đặt gần nhau, và chuỗi sẽ được căn giữa trong phần tử cha.

        // 2. Constrain Box1
        Box(
            modifier = Modifier
                .size(100.dp) // Đặt kích thước cho Box1
                .background(Color.Red) // Đặt màu nền cho Box1
                .constrainAs(box1) {
                    // constraintAs cho phép đặt ràng buộc cho phần tử con dựa trên tham chiếu đã tạo (box1)
                    top.linkTo(
                        parent.top,
                        margin = 100.dp
                    ) // Liên kết đỉnh của Box1 với đỉnh của phần tử cha (ConstraintLayout), cách nhau 16dp
//                    start.linkTo(parent.start, margin = 40.dp) // Liên kết bên trái của Box1 với bên trái của phần tử cha, cách nhau 16dp
                    start.linkTo(guideLine1) // Liên kết bên trái của Box1 với guideline đã tạo, cách trái của phần tử cha 15%
                }
        )

        // 3. Constrain Box2
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Green)
                .constrainAs(box2) {
                    // constraintAs cho phép đặt ràng buộc cho phần tử con dựa trên tham chiếu đã tạo (box2)
                    top.linkTo(
                        box1.bottom,
                        margin = 20.dp
                    ) // Liên kết đỉnh của Box2 với đáy của Box1, cách nhau 16dp
//                    start.linkTo(parent.start, margin = 30.dp) // Liên kết bên trái của Box2 với bên trái của phần tử cha, cách nhau 16dp
                    start.linkTo(guideLine1) // Liên kết bên trái của Box2 với guideline đã tạo, cách trái của phần tử cha 15%
                }
        )

        // 4. Constrain Text
        Text(
            text = "Hello, ConstraintLayout!",
            modifier = Modifier.constrainAs(text) {
                // constraintAs cho phép đặt ràng buộc cho phần tử con dựa trên tham chiếu đã tạo (text)
                top.linkTo(
                    box2.bottom,
                    margin = 16.dp
                ) // Liên kết đỉnh của Text với đáy của Box2, cách nhau 16dp
//                end.linkTo(parent.end, margin = 16.dp) // Liên kết bên phải của Text với bên phải của phần tử cha, cách nhau 16dp
//                start.linkTo(guideLine1) // Liên kết bên trái của Text với guideline đã tạo, cách trái của phần tử cha 15%
                start.linkTo(
                    barrier1,
                    margin = 16.dp
                ) // Liên kết bên trái của Text với barrier đã tạo, cách trái của barrier 16dp (đảm bảo rằng Text sẽ không chồng lên Box1 hoặc Box2)
            }
        )

        // Optional: Chains
        createVerticalChain(
            box1, box2,
            chainStyle = ChainStyle.Spread
        ) // Tạo một chuỗi dọc giữa Box1 và Box2 với kiểu chuỗi Spread, giúp phân phối đều không gian giữa chúng
    }

}