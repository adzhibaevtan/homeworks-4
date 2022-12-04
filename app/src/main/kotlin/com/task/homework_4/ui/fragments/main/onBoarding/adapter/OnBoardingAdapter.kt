package com.task.homework_4.ui.fragments.main.onBoarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.task.homework_4.R
import com.task.homework_4.databinding.ItemOnBoardingBinding
import com.task.homework_4.ui.models.OnBoard

class OnBoardingAdapter : Adapter<OnBoardingAdapter.OnBoardingsViewHolder>() {
    private val list = arrayListOf(
        OnBoard(R.raw.lottie1, "Track your tasks progress", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"),
        OnBoard(R.raw.lottie2, "Your personal task manager", "Lorem ipsum dolor sit amet, consectetur adipiscing elit,  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"),
        OnBoard(R.raw.lottie3, "Complete tasks easily", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
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
        boarding.image?.let { binding.vpImage.setAnimation(it) }
            vpTitle.text = boarding.title
            vpDesc.text = boarding.desc
        }

    }
}