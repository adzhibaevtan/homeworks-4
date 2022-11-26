package com.task.homework_4.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.task.homework_4.R
import com.task.homework_4.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val fileChooserContract = createFileChooserContractToGetImageUri { imageUri ->
        binding.imAvatar.setImageURI(imageUri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAvatar()
    }

    private fun selectAvatar() {
        binding.imAvatar.setOnClickListener {
            fileChooserContract.launch("image/*")
        }
    }
}

fun Fragment.createFileChooserContractToGetImageUri(actionWithImageUri: (imageUri: Uri?) -> Unit) =
    registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        imageUri?.let {
            actionWithImageUri(imageUri)
        }
    }