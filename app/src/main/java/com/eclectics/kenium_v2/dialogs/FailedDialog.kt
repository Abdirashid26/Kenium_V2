package com.eclectics.kenium_v2.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.eclectics.kenium_v2.R
import com.google.android.material.button.MaterialButton

class FailedDialog(private val title : String, private val text : String) : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = activity?.layoutInflater?.inflate(R.layout.failed_dialog,null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(v)
            .create()
        val textView = v?.findViewById<TextView>(
            R.id.dialogText
        )
        textView?.setText(text)

        val textView2 = v?.findViewById<TextView>(
            R.id.dialogTitle
        )
        textView2?.setText(title)

        val dismissBtnv = v?.findViewById<MaterialButton>(
            R.id.dimissBtn
        )

        dismissBtnv?.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.setCanceledOnTouchOutside(false)
        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.curved_borders);
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    companion object {
            const val TAG = "Failed Dialog"
    }
}