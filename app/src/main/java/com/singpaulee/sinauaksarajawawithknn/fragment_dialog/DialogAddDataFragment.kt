package com.singpaulee.sinauaksarajawawithknn.fragment_dialog


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
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
import kotlinx.android.synthetic.main.fragment_dialog_add_data.*
import kotlinx.android.synthetic.main.fragment_dialog_add_data.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_isAlertDialog = "isAlertDialog"
private const val ARG_isFullscreen = "isFullScreen"
private const val ARG_RESULT = "result"

/**
 * A simple [Fragment] subclass.
 *
 */
class DialogAddDataFragment : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_add_data, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Check arguments
        if (arguments!=null){
            if (!arguments?.getBoolean(ARG_isAlertDialog)!!){
                val dialog = super.onCreateDialog(savedInstanceState)
                dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                dialog.setOnKeyListener(DialogInterface.OnKeyListener { mDialog, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        val dialogListener = activity as DialogListener
                        dialogListener.onFinishEditDialog("reset")
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val correctAnimation = "lottie_correct_answer.json"
        val wrongAnimation = "lottie_failed_answer.json"
        if (arguments?.getBoolean(ARG_RESULT)!!){
            view.daaf_tv_title.text = "Jawabanmu Bener!!!"
            view.daaf_lottie_animation.setAnimation(correctAnimation)
        }else{
            view.daaf_tv_title.text = "Jawabanmu Salah!!!"
            view.daaf_lottie_animation.setAnimation(wrongAnimation)
        }
        view.daaf_lottie_animation.playAnimation()

        view.daaf_btn_repeat.setOnClickListener(this)
        view.daaf_btn_change_aksara.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            daaf_btn_repeat -> {
                val dialogListener = activity as DialogListener
                dialogListener.onFinishEditDialog("reset")
                dismiss()
            }
            daaf_btn_change_aksara -> {
                val dialogListener = activity as DialogListener
                dialogListener.onFinishEditDialog("change")
                dismiss()
            }
        }
    }

    interface DialogListener {
        fun onFinishEditDialog(message: String)
    }

    
}
