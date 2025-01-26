package com.example.firstapp

sealed class Screen(val route: String) {
    object RecipeScreen :
        Screen("recipescreen") // Định nghĩa một object RecipeScreen kế thừa từ Screen

    object DetailScreen :
        Screen("detailscreen") // Định nghĩa một object DetailScreen kế thừa từ Screen
}

/*
* Đây là một sealed class Screen, một sealed class là một class mà chỉ có thể được kế thừa bởi các class nằm trong cùng một file với nó.
 */