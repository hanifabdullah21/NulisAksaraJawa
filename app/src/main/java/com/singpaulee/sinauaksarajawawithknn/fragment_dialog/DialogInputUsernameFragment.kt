package com.singpaulee.sinauaksarajawawithknn.fragment_dialog


import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.singpaulee.sinauaksarajawawithknn.R
import kotlinx.android.synthetic.main.fragment_dialog_input_username.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_isAlertDialog = "isAlertDialog"
private const val ARG_isFullscreen = "isFullScreen"
private const val ARG_RESULT = "result"

/**
 * A simple [Fragment] subclass.
 *
 */
class DialogInputUsernameFragment : DialogFragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_input_username, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Check arguments
        if (arguments!=null){
            if (!arguments?.getBoolean(ARG_isAlertDialog)!!){
                val dialog = super.onCreateDialog(savedInstanceState)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setOnKeyListener(DialogInterface.OnKeyListener { mDialog, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        val dialogListener = activity as DialogListener
//                        dialogListener.onFinishDialog(null)
                        activity?.finish()
                        dismiss()
                        return@OnKeyListener true
                    }
                    false
                })
                return dialog
            }
        }

        //Create builder when this is an alert dialog
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("")
        builder.setMessage("")
        builder.setPositiveButton("") { dialog, which -> }
        return builder.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isFullscreen = false
        if (arguments!=null) {
            isFullscreen = requireNotNull(arguments?.getBoolean(ARG_isFullscreen))
        }
        if (isFullscreen) setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diuf_btn_start_quiz.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            diuf_btn_start_quiz -> {
                if (diuf_edt_username.text.toString().isBlank()){
                    diuf_edt_username.error = "Isi jenengmu ndisik"
                    diuf_edt_username.requestFocus()
                    return
                }
                val dialogListener = activity as DialogListener
                dialogListener.onFinishDialog(diuf_edt_username.text.toString())
                dismiss()
            }
        }
    }

    interface DialogListener{
        fun onFinishDialog(username: String?)
    }
}
