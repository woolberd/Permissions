package com.example.permissions.ui.activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.permissions.databinding.ActivityMainBinding

private  const val PERMISSION_REQUEAST = 10

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pickImage = 100
    private lateinit var uri: Uri
    private lateinit var context: Context
    private val permission = arrayOf(READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPermission()
    }

    private fun setPermission() {
        binding.btnGallery.setOnClickListener {
            when {
                checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    fileChooserContracts.launch("image/*")
                }
                !shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) -> {
                    chekPermission(permission)

                }
                else -> {
                    requestPermissions(
                        arrayOf(READ_EXTERNAL_STORAGE),
                        pickImage
                    )
                    dialog()
                }
            }
        }
    }

    private fun chekPermission(permissionOnArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionOnArray.indices) {
            if (checkCallingOrSelfPermission(permissionOnArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEAST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain = shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(context, "Permission danied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Go to settings", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val fileChooserContracts =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                binding.actionImage.setImageURI(imageUri)
                uri = imageUri
            }
        }

    private fun dialog() {
    }
}

