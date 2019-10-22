package com.example.student.android04c01

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class OnExitDialogFragment : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage(R.string.do_you_want_exit)
            .setPositiveButton(R.string.yes) { _, _ -> activity!!.finish()}
            .setNegativeButton(R.string.cancel){ _, _ ->
            }
        return builder.create()
    }


}