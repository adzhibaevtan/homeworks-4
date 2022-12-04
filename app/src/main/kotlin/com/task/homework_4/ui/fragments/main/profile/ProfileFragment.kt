package com.task.homework_4.ui.fragments.main.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.task.homework_4.R
import com.task.homework_4.data.local.preferences.PreferencesManager
import com.task.homework_4.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val preferencesManager by lazy {
        PreferencesManager(
            requireContext().getSharedPreferences(
                "homework4.preferences",
                Context.MODE_PRIVATE
            )
        )
    }

    private val fileChooserContract = createFileChooserContractToGetImageUri { imageUri ->
        binding.imAvatar.setImageURI(imageUri)
        preferencesManager.profileImagePath = imageUri.toString()
    }
    private val createReadExternalStoragePermissionLauncher =
        createRequestPermissionLauncherToRequestSinglePermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            {
                fileChooserContract.launch("image/*")
            })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAvatar()
        extractNameAndAge()
        validateNameAndAgeAndSave()
        preferencesManager.profileImagePath?.let {
            Glide.with(binding.imAvatar).load(Uri.parse(preferencesManager.profileImagePath))
                .into(binding.imAvatar)
        }
    }

    private fun validateNameAndAgeAndSave() = with(binding) {
        btnSave.setOnClickListener {
            preferencesManager.name = when (etProfileName.text.toString().isNotEmpty()) {
                true -> {
                    hideSoftKeyboard()
                    etProfileName.text.toString()
                }
                false -> {
                    etProfileName.error = "Field is empty"
                    ""
                }
            }
            preferencesManager.age = when (etProfileAge.text.toString().isNotEmpty()) {
                true -> {
                    hideSoftKeyboard()
                    etProfileAge.text.toString()
                }
                false -> {
                    etProfileAge.error = "Field is empty"
                    ""
                }
            }
        }
    }

    private fun extractNameAndAge() {
        preferencesManager.name?.let {
            binding.etProfileName.setText(it)
        }
        preferencesManager.age?.let {
            binding.etProfileAge.setText(it)
        }
    }

    private fun selectAvatar() {
        binding.imAvatar.setOnClickListener {
            checkForPermissionStatusAndRequestIt(
                createReadExternalStoragePermissionLauncher,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                actionWhenPermissionHasBeenGranted = {
                    fileChooserContract.launch("image/*")
                })
        }
    }
}

fun Fragment.createFileChooserContractToGetImageUri(actionWithImageUri: (imageUri: Uri?) -> Unit) =
    registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        imageUri?.let {
            actionWithImageUri(imageUri)
        }
    }

fun Fragment.createRequestPermissionLauncherToRequestSinglePermission(
    permission: String,
    actionWhenPermissionHasBeenGranted: (() -> Unit)? = null,
    actionWhenPermissionHasBeenDenied: (() -> Unit)? = null
): ActivityResultLauncher<String> {
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionGranted ->
            when {
                isPermissionGranted -> actionWhenPermissionHasBeenGranted?.invoke()
                !shouldShowRequestPermissionRationale(permission) -> actionWhenPermissionHasBeenDenied?.invoke()
            }
        }
    return requestPermissionLauncher
}

fun Fragment.checkForPermissionStatusAndRequestIt(
    requestPermissionLauncher: ActivityResultLauncher<String>,
    permission: String,
    actionWhenPermissionHasBeenGranted: (() -> Unit)? = null
): Boolean {
    return when (ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED) {
        true -> {
            actionWhenPermissionHasBeenGranted?.invoke()
            true
        }

        else -> {
            requestPermissionLauncher.launch(permission)
            false
        }
    }
}

fun Fragment.hideSoftKeyboard() {
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}