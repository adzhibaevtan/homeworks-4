package com.task.homework_4.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.task.homework_4.R
import com.task.homework_4.data.local.preferences.PreferencesManager
import com.task.homework_4.databinding.FragmentInputPhoneNumberBinding
import com.task.homework_4.ui.fragments.main.profile.hideSoftKeyboard
import com.vicmikhailau.maskededittext.MaskedFormatter
import com.vicmikhailau.maskededittext.MaskedWatcher
import java.util.concurrent.TimeUnit

class InputPhoneNumberFragment : Fragment(R.layout.fragment_input_phone_number) {
    private val binding by viewBinding(FragmentInputPhoneNumberBinding::bind)
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val formatter = MaskedFormatter("### ### ###")

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
        setMaskToEditText()
        enableButtonDependingOnThePhoneNumberLength()
        sendVerificationCodeAndProceed()
    }

    private fun setMaskToEditText() {
        binding.etPhoneNumber.addTextChangedListener(
            MaskedWatcher(
                formatter,
                binding.etPhoneNumber
            )
        )
    }

    private fun enableButtonDependingOnThePhoneNumberLength() {
        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnSendVerificationCode.isEnabled = when (s.toString().length == 11) {
                    true -> {
                        binding.btnSendVerificationCode.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.purple_500
                            )
                        )
                        true
                    }
                    false -> {
                        binding.btnSendVerificationCode.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray
                            )
                        )
                        false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun sendVerificationCodeAndProceed() {
        binding.btnSendVerificationCode.setOnClickListener {
            hideSoftKeyboard()
            val options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                    .setPhoneNumber(
                        binding.tlPhoneNumber.prefixText.toString() + formatter.formatString(binding.etPhoneNumber.text.toString())?.unMaskedString.toString(),
                    )
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(requireActivity())
                    .setCallbacks(providePhoneAuthCallbacks())
                    .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun providePhoneAuthCallbacks() =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {

                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(
                        requireContext(),
                        e.localizedMessage?.toString() ?: "An error occurred!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(
                        requireContext(),
                        e.localizedMessage?.toString() ?: "An error occurred!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                Toast.makeText(
                    requireContext(),
                    "Verification code has been sent to your phone number",
                    Toast.LENGTH_LONG
                ).show()
                preferencesManager.verificationId = verificationId
                findNavController().navigate(R.id.action_inputPhoneNumberFragment_to_verifyPhoneNumberWithCodeFragment)
            }
        }
}