package com.example.permissions.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.permissions.databinding.FragmentDialogBinding

class DialogFragment1 : DialogFragment() {

    private lateinit var binding: FragmentDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        clickSettings()
//        clickCancel()
        click()
    }

    private fun click() {
        binding.no.setOnClickListener {
            dismiss()
        }
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            }
        binding.yes.setOnClickListener {
            resultLauncher.launch(
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                    Uri.parse("package:${requireActivity().packageName}")
                )
            )
            dismiss()
        }

    }


//    private fun clickCancel() = with(binding) {
//        no.setOnClickListener {
//            findNavController().navigateUp()
//        }
//    }
//
//    private fun clickSettings() = with(binding) {
//        yes.setOnClickListener {
//            startActivity(Intent(Settings.ACTION_SETTINGS))
//        }
//    }
}
