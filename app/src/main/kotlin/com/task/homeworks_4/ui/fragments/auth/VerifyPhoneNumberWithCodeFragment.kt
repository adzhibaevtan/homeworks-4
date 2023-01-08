package com.task.homeworks_4.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.task.homeworks_4.R
import com.task.homeworks_4.data.local.preferences.PreferencesManager
import com.task.homeworks_4.databinding.FragmentVerifyPhoneNumberWithCodeBinding
import com.task.homeworks_4.ui.fragments.main.profile.hideSoftKeyboard

class VerifyPhoneNumberWithCodeFragment :
    Fragment(R.layout.fragment_verify_phone_number_with_code) {
    private val binding by viewBinding(FragmentVerifyPhoneNumberWithCodeBinding::bind)
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val preferencesManager by lazy {
        PreferencesManager(
            requireContext().getSharedPreferences(
                "homework4.preferences",
                    Context.MODE_PRIVATE
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.pvVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length == 6) {
                    hideSoftKeyboard()
                    signInWithPhoneAuthCredential(verifyPhoneNumberWithCode(s.toString()))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    fun verifyPhoneNumberWithCode(
        code: String,
    ) =
        PhoneAuthProvider.getCredential(preferencesManager.verificationId.toString(), code)

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) =
        with(binding.pvVerificationCode) {
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        preferencesManager.isAuthenticated = true
                        setLineColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green
                            )
                        )
                        handler.postDelayed({
                            findNavController().navigate(R.id.action_verifyPhoneNumberWithCodeFragment_to_navigation_home)
                        }, 1000L)
                    } else {
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            text?.clear()
                            setLineColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                            handler.postDelayed({
                                setLineColor(
                                    ContextCompat.getColor(
                                        requireContext(), R.color.gray
                                    )
                                )
                            }, 2000L)
                        }
                    }
                }
        }
}