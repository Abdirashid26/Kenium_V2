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

class ProgressDialog (private val text : String) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = activity?.layoutInflater?.inflate(R.layout.progress_dialog,null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(v)
            .create()
        val textView = v?.findViewById<TextView>(
            R.id.text
        )
        textView?.setText(text)
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
        const val TAG = "View Balance Dialog"
    }
}