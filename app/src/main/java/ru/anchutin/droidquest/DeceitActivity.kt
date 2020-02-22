package ru.anchutin.droidquest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DeceitActivity : AppCompatActivity() {

    private lateinit var mAnswerTextView: TextView
    private lateinit var mShowAnswer: Button

    companion object {
        val EXTRA_ANSWER_IS_TRUE = "ru.rsue.android.droidquest.answer_is_true"
        val EXTRA_ANSWER_SHOWN = "ru.rsue.android.droidquest.answer_shown"

        fun newIntent(packageContext: Context?, answerIsTrue: Boolean): Intent? {
            val intent = Intent(packageContext, DeceitActivity::class.java)
            return intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
        }

        fun  wasAnswerShown(result: Intent) =
            result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deceit)
        val mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswer = findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener {
            mAnswerTextView.setText(
                if (mAnswerIsTrue) R.string.true_button else R.string.false_button
            )
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK, data)
    }

}
