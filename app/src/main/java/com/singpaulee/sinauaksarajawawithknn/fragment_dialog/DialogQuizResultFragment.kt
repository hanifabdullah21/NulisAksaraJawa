package com.singpaulee.sinauaksarajawawithknn.fragment_dialog


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.connection.AppConfig
import com.singpaulee.sinauaksarajawawithknn.connection.DataInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dialog_quiz_result.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_FULLSCREEN = "isFullScreen"
private const val ARG_SKOR = "skor"
private const val ARG_USERNAME = "username"

/**
 * A simple [Fragment] subclass.
 *
 */
class DialogQuizResultFragment : DialogFragment() {

    lateinit var username: String
    var score: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isFullscreen = false
        if (arguments!=null) {
            isFullscreen = requireNotNull(arguments?.getBoolean(ARG_FULLSCREEN))
        }
        if (isFullscreen) setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogFragment = super.onCreateDialog(savedInstanceState)

        //if you want transparent
        //dialogFragment.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogFragment.setOnKeyListener { dialog, keyCode, event ->
            when(keyCode){
                KeyEvent.KEYCODE_BACK -> {
                    (activity as DialogListener).onFinishEditDialog("backToMenu")
                    dismiss()
                    return@setOnKeyListener true
                }
                else -> {
                    return@setOnKeyListener false
                }
            }
        }

        return dialogFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_quiz_result, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val victory = "victory.json"

        username = arguments?.getString(ARG_USERNAME).toString()
        score = arguments?.getInt(ARG_SKOR)

        view.dqrf_tv_skor.text = score.toString()
        view.dqrf_tv_title.text = username+", SKORMU"

        view.dqrf_lottie_animation.setAnimation(victory)
        view.dqrf_lottie_animation.playAnimation()

        view.dqrf_btn_back_to_menu.onClick {
            (activity as DialogListener).onFinishEditDialog("backToMenu")
            dismiss()
        }
    }

    interface DialogListener {
        fun onFinishEditDialog(message: String)
    }
}
