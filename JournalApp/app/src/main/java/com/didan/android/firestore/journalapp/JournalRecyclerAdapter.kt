package com.didan.android.firestore.journalapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.didan.android.firestore.journalapp.databinding.JournalRowBinding

class JournalRecyclerAdapter(val context: Context, var journalList: List<Journal>) :
    RecyclerView.Adapter<JournalRecyclerAdapter.MyViewHolder>() {

    lateinit var binding: JournalRowBinding // Biến binding cho layout journal_row

    inner class MyViewHolder(var binding: JournalRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: Journal) {
            binding.journal = journal // Gán dữ liệu journal cho binding
        }
    }

    // Tạo ViewHolder cho từng item trong RecyclerView
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // Inflate layout journal_row và tạo binding
        binding = JournalRowBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    // Gán dữ liệu cho ViewHolder tại vị trí cụ thể
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val journal = journalList[position] // Lấy dữ liệu journal tại vị trí hiện tại
        holder.bind(journal) // Gọi hàm bind để gán dữ liệu cho ViewHolder
    }

    // Hàm trả về số lượng item trong danh sách journal
    override fun getItemCount(): Int {
        return journalList.size
    }
}