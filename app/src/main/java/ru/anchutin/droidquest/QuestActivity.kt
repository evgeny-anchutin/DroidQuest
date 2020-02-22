package ru.anchutin.droidquest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class QuestActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() вызван")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() вызван")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() вызван")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() вызван")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() вызван")
    }

    companion object {
        private val TAG  = "QuestActivity"
        private val KEY_INDEX = "index"
        private val REQUEST_CODE_DECEIT = 0
    }

    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: Button
    private lateinit var mPrevButton: Button
    private lateinit var mQuestionTextView: TextView
    private lateinit var mDeceitButton: Button
    private lateinit var mQuestionBank: List<Question>


    private var mCurrentIndex = 0

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState!!.putInt(KEY_INDEX, mCurrentIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest)
        Log.d(TAG, "onCreate(Bundle) вызван")
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)

        }
        mQuestionBank = QuestionBank.instance.allQuestions

        mQuestionTextView = findViewById(R.id.question_text_view)
        updateQuestion()
        mTrueButton = findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }

        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            checkAnswer(false)
        }
        mNextButton = findViewById(R.id.next_button)
        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        mPrevButton = findViewById(R.id.prev_button)
        mPrevButton.setOnClickListener {
            mCurrentIndex = if (mCurrentIndex - 1 < 0) mQuestionBank.size - 1 else mCurrentIndex - 1
            updateQuestion()
        }

        mDeceitButton = findViewById(R.id.deceit_button)
        mDeceitButton.setOnClickListener{
            val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
            val intent = DeceitActivity.newIntent(this, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_DECEIT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_DECEIT) {
            if (data == null) {
                return;
            }
            mQuestionBank[mCurrentIndex].isDeceit = DeceitActivity.wasAnswerShown(result = data)
        }
    }

        private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
        val messageResId = if (mQuestionBank[mCurrentIndex].isDeceit) R.string.judgment_toast
        else if (userPressedTrue == answerIsTrue) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }


}
