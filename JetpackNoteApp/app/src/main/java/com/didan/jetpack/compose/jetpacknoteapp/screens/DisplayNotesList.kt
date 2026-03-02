package com.didan.jetpack.compose.jetpacknoteapp.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.didan.jetpack.compose.jetpacknoteapp.roomdb.Note

@Composable
fun DisplayNotesList(notes: List<Note>) {
    // LazyVerticalStaggeredGrid là một thành phần trong thư viện Compose Foundation, được sử dụng để hiển thị một danh sách các phần tử theo dạng lưới với các hàng có chiều cao khác nhau. Điều này rất hữu ích khi bạn muốn hiển thị các ghi chú có nội dung khác nhau, giúp tận
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2), // Số cột trong lưới cố định là 2
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 38.dp), // Chiếm toàn bộ kích thước của bố cục cha
        contentPadding = PaddingValues(16.dp) // Thêm khoảng cách giữa các phần tử trong lưới
    ) {
        items(notes) { note ->
            NoteListItem(note = note) // Hiển thị mỗi ghi chú bằng cách sử dụng NoteListItem
        }
    }
}