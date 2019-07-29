package com.singpaulee.sinauaksarajawawithknn.activity

import android.animation.Animator
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.connection.AppConfig
import com.singpaulee.sinauaksarajawawithknn.connection.DataInterface
import com.singpaulee.sinauaksarajawawithknn.fragment_dialog.DialogInputUsernameFragment
import com.singpaulee.sinauaksarajawawithknn.fragment_dialog.DialogQuizResultFragment
import com.singpaulee.sinauaksarajawawithknn.helper.ListHanacaraka
import com.singpaulee.sinauaksarajawawithknn.helper.ListQuiz
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_quiz.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

class QuizActivity : AppCompatActivity(), DialogQuizResultFragment.DialogListener, DialogInputUsernameFragment.DialogListener {


    var quiz: ListQuiz? = null

    var username: String = ""
    var skor: Int = 0
    var numberOfQuestion = 0    //Jumlah Pertanyaan yang sudah keluar
    var indexQuestion = -1      //Index pertanyaan yang tampil saat ini

    var listIndex : ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        hideAllView(true)
        openDialogInputUsername()

        quiz = ListQuiz()

        setupQuestion()

        qa_rg_answer.onCheckedChange { group, checkedId ->
            if (group?.checkedRadioButtonId != -1){
                val selectedAnswer = findViewById<RadioButton>(checkedId).text.toString()
                checkSelectedAnswer(selectedAnswer)
            }
        }
    }

    private fun openDialogInputUsername(){

        val dialogFragment = DialogInputUsernameFragment()
        val bundle = Bundle()
        bundle.putBoolean("isFullScreen", true)
        dialogFragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, "dialog")
    }

    private fun hideAllView(isHide: Boolean) {
        if (isHide){
            qa_tv_name.visibility = View.GONE
            qa_tv_skor.visibility = View.GONE
            qa_ll_question_aksara.visibility = View.GONE
            qa_ll_choice_answer.visibility = View.GONE
        }else{
            qa_tv_name.visibility = View.VISIBLE
            qa_tv_skor.visibility = View.VISIBLE
            qa_ll_question_aksara.visibility = View.VISIBLE
            qa_ll_choice_answer.visibility = View.VISIBLE
        }
    }

    private fun openDialogResult(){
        val dialogFragment = DialogQuizResultFragment()
        val bundle = Bundle()
        bundle.putBoolean("isFullScreen", true)
        bundle.putInt("skor", skor)
        bundle.putString("username", username)
        dialogFragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, "dialog")
    }

    @SuppressLint("SetTextI18n")
    private fun playAnimation(isCorrect: Boolean) {
        val correctAnimation = "lottie_fireworks.json"
        val wrongAnimation = "lottie_failed_answer.json"

        qa_fr_animation.visibility = View.VISIBLE
        qa_rb_answer1.isEnabled = false
        qa_rb_answer2.isEnabled = false
        qa_rb_answer3.isEnabled = false

        if (isCorrect){
            qa_tv_result_answer.text = "Jawabanmu bener!!!"
            qa_lottie_animation_answer.setAnimation(correctAnimation)
            qa_lottie_animation_answer.repeatCount = 2
        }else{
            qa_tv_result_answer.text = "Jawabanmu salah!!!"
            qa_lottie_animation_answer.setAnimation(wrongAnimation)
            qa_lottie_animation_answer.repeatCount = 3
        }
        qa_lottie_animation_answer.playAnimation()
        qa_lottie_animation_answer.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.d("QUIZ","STOP ANIMATING")
                qa_fr_animation.visibility = View.GONE
                qa_rb_answer1.isEnabled = true
                qa_rb_answer2.isEnabled = true
                qa_rb_answer3.isEnabled = true
                qa_rg_answer.clearCheck()
                setupQuestion()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }

    private fun checkSelectedAnswer(selectedAnswer: String) {
        val isCorrect: Boolean
//        toast(selectedAnswer+" "+quiz?.getAnswer(indexQuestion))
        if (selectedAnswer == quiz?.getAnswer(indexQuestion)){
            isCorrect = true
            skor += 10
        }else{
            skor += 0
            isCorrect = false
        }
        qa_tv_skor.text = "Skor: $skor"
        playAnimation(isCorrect)
    }

    private fun setupQuestion() {
        qa_lottie_animation_answer.removeAllAnimatorListeners()
        if (numberOfQuestion<10){
            Log.d("QUIZ", "random")
            indexQuestion = randomNumber()
            if (indexQuestion==-1){
                setupQuestion()
            }else{
                numberOfQuestion++
                listIndex.add(indexQuestion)

                Log.d("INDEXNUM", indexQuestion.toString())

                qa_tv_aksara.text = quiz?.getAksaraQuestion(indexQuestion)
                qa_tv_question.text = quiz?.getQuestion(indexQuestion)
                qa_rb_answer1.text = quiz?.getChoiceAnswer1(indexQuestion)
                qa_rb_answer2.text = quiz?.getChoiceAnswer2(indexQuestion)
                qa_rb_answer3.text = quiz?.getChoiceAnswer3(indexQuestion)
            }
        }else{
            Log.d("QUIZ", "stop")
            hideAllView(true)
            postScore()
        }
    }

    @SuppressLint("CheckResult")
    private fun postScore() {
        val postNewScore = AppConfig.retrofitConfig(this)
            .create(DataInterface::class.java)
            .postNewScore("postNewHighScore", username, skor)


        postNewScore.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.success as Boolean){
                    openDialogResult()
                }else{
                    toast("ERROR")
                }
            }, {

            })
    }


    /** Fungsi ini digunakan untuk mengacak angka antara angka 0-20
     * dan akan mengembalikan nilai angka bertipe integer
     *
     * */
    private fun randomNumber(): Int {
        val randomGenerator = Random()
        var num = randomGenerator.nextInt(20)
        if (listIndex.contains(num)){
            return -1
        }else{
            return num
        }
    }

    override fun onFinishEditDialog(message: String) {
        when(message){
            "backToMenu" -> finish()
        }
    }

    override fun onFinishDialog(username: String?) {
        if (username==null){
            finish()
        }else{
            this.username = username
            qa_tv_skor.text = "Skor: $skor"
            qa_tv_name.text = "Jeneng: $username"

            hideAllView(false)
        }
    }
}
