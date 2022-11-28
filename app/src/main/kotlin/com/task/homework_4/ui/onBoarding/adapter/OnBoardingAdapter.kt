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
        OnBoard("https://i.giphy.com/media/3oKIPD7IWVJ7UYXaUw/giphy.gif", "1", "1.1"),
        OnBoard("https://i.giphy.com/media/Y08bx6Fea1BafzTlvc/giphy.gif", "2", "2.1"),
        OnBoard("https://i.giphy.com/media/3oKIPaihAcHpS6iFPO/giphy.gif", "3", "3.1")
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