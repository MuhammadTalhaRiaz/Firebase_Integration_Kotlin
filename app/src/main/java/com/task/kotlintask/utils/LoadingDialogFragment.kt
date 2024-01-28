package com.task.kotlintask.utils

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.janbark.kotlintask.R

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.alert_dialog, null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(view)
        dialog.setCancelable(false) // Set to true if you want to allow canceling with the back button
        return dialog
    }
}