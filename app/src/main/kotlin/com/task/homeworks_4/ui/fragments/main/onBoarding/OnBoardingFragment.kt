package com.task.homeworks_4.ui.fragments.main.onBoarding

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import com.task.homeworks_4.R
import com.task.homeworks_4.data.local.preferences.PreferencesManager
import com.task.homeworks_4.databinding.FragmentOnBoardingBinding
import com.task.homeworks_4.ui.fragments.main.onBoarding.adapter.OnBoardingAdapter

class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {
    private val binding by viewBinding(FragmentOnBoardingBinding::bind)
    private val onBoardAdapter = OnBoardingAdapter()
    private val preferencesManager by lazy {
        PreferencesManager(
            requireContext().getSharedPreferences(
                "homework4.preferences",
                Context.MODE_PRIVATE
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = onBoardAdapter
        navigateToHomeIfUserHasSeenOnboard()
        setupListeners()

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                start.isVisible = position == onBoardAdapter.itemCount - 1
                skip.isVisible = position != onBoardAdapter.itemCount - 1
            }
        })
        dotsIndicator.attachTo(viewPager)
    }

    private fun setupListeners() {
        listOf<View>(binding.skip, binding.start).forEach {
            it.setOnClickListener {
                navigateToHome()
            }
        }
    }

    private fun navigateToHomeIfUserHasSeenOnboard() {
        if (preferencesManager.hasUserSeenOnBoarding) {
            findNavController().navigate(R.id.action_onBoardingFragment_to_inputPhoneNumberFragment)
        }
    }

    private fun navigateToHome() {
        preferencesManager.hasUserSeenOnBoarding = true
        findNavController().navigate(R.id.action_onBoardingFragment_to_inputPhoneNumberFragment)
    }
}