package com.didan.jetpack.compose.composables

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.didan.jetpack.compose.composables.ui.theme.ComposablesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposablesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TextCompose(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TextCompose(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 26.dp)
            .border(2.dp, Color.Yellow)
            .padding(12.dp)
            .border(4.dp, Color.Green)
            .padding(8.dp)
    ) {
        Text(
            text = "Hello $name!",
            color = Color.Red,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive,
            textDecoration = TextDecoration.combine(
                listOf(
                    TextDecoration.LineThrough,
                    TextDecoration.Underline
                )
            ),
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )

        Text(
            text = "This is a simple text composable example.",
            color = Color.Blue,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.Serif,
            textDecoration = TextDecoration.None,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 2.dp)
        )

        // Lấy hình ảnh từ tài nguyên và hiển thị nó (sử dụng hàm painterResource)
        val myPainter = painterResource(R.drawable.electronics)
        Image(
            painter = myPainter,
            contentDescription = "Electronics Image",
            modifier = Modifier.padding(16.dp)
        )

        Button(onClick = {
            Log.d("TextCompose", "Button clicked!")
        }) {
            Text(text = "Button")
        }

        FilledTonalButton(onClick = {
            Log.d("TextCompose", "FilledTonalButton clicked!")
        }) {
            Text(text = "FilledTonalButton")
        }

        ElevatedButton(
            onClick = {
                Log.d("TextCompose", "ElevatedButton clicked!")
            },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Text(text = "ElevatedButton")
        }

        OutlinedButton(
            onClick = {
                Log.d("TextCompose", "OutlinedButton clicked!")
            }
        ) {
            Text(text = "OutlinedButton")
        }

        TextButton(
            onClick = {
                Log.d("TextCompose", "TextButton clicked!")
            }
        ) {
            Text(text = "TextButton")
        }

        // Biến trạng thái để lưu trữ giá trị của TextField
        var text by remember {
            mutableStateOf(TextFieldValue()) // mutableStateOf hoạt động như Observable, khi giá trị của text thay đổi, TextField sẽ tự động cập nhật giao diện
        }

        TextField(value = text, onValueChange = { text = it })

        // Biến trạng thái để lưu trữ trạng thái của checkbox
        var isChecked by remember {
            mutableStateOf(false) // mutableStateOf hoạt động như Observable, khi giá trị của isChecked thay đổi, Checkbox sẽ tự động cập nhật giao diện
        }
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                Log.d("TextCompose", "Checkbox state changed: $isChecked")
            }
        )

        // Biến trạng thái để lưu trữ trạng thái của switch
        var isSwitched by remember {
            mutableStateOf(false) // mutableStateOf hoạt động như Observable, khi giá trị của isSwitched thay đổi, Switch sẽ tự động cập nhật giao diện
        }
        Switch(
            checked = isSwitched,
            onCheckedChange = {
                isSwitched = it
                Log.d("TextCompose", "Switch state changed: $isSwitched")
            }
        )

        // Biến trạng thái để lưu trữ giá trị của radio button
        var selectedOption by remember {
            mutableStateOf("Option 1") // mutableStateOf hoạt động như Observable, khi giá trị của selectedOption thay đổi, giao diện sẽ tự động cập nhật
        }
        Column(

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == "Option 1",
                    onClick = {
                        selectedOption = "Option 1"
                        Log.d("TextCompose", "Selected: $selectedOption")
                    }
                )

                Text(text = "Option 1", modifier = Modifier.padding(start = 8.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == "Option 2",
                    onClick = {
                        selectedOption = "Option 2"
                        Log.d("TextCompose", "Selected: $selectedOption")
                    }
                )

                Text(text = "Option 2", modifier = Modifier.padding(start = 8.dp))
            }
        }

        // Thanh tiến trình (ProgressBar)
        CircularProgressIndicator(
            progress = { 6f / 10f },
            modifier = Modifier.padding(top = 16.dp)
        ) // Hiển thị thanh tiến trình với giá trị 60%

        LinearProgressIndicator(
            progress = { 6f / 10f },
            modifier = Modifier.padding(top = 16.dp),
            color = Color.Red,
            trackColor = Color.Yellow
        )




    }
}

@Preview(showBackground = true)
@Composable
fun TextComposePreview() {
    ComposablesTheme {
        TextCompose("Android")
    }
}