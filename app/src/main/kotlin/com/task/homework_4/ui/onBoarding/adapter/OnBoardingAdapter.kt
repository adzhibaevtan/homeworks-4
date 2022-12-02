package com.task.homework_4.ui.onBoarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.task.homework_4.databinding.ItemOnBoardingBinding
import com.task.homework_4.ui.models.OnBoard

class OnBoardingAdapter : Adapter<OnBoardingAdapter.OnBoardingsViewHolder>() {
    private val list = arrayListOf(
        OnBoard("https://user-images.githubusercontent.com/98197909/205021390-e13baf5c-7277-42f1-8cf1-4bad466c8c70.gif", "Track your tasks progress", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"),
        OnBoard("https://user-images.githubusercontent.com/98197909/205022211-949b2712-18df-4ada-94ae-bafc51e2c3c1.gif", "Your personal task manager", "Lorem ipsum dolor sit amet, consectetur adipiscing elit"),
        OnBoard("https://user-images.githubusercontent.com/98197909/205023279-798f542a-7bc1-40f8-87a7-80c076db76ab.gif", "Complete tasks easily", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OnBoardingsViewHolder(
        ItemOnBoardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OnBoardingsViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    inner class OnBoardingsViewHolder(private val binding: ItemOnBoardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(boarding: OnBoard) = with(binding) {
        Glide.with(vpImage).asGif().load(boarding.imageUrl).into(vpImage);
            vpTitle.text = boarding.title
            vpDesc.text = boarding.desc
        }
    }
}