package ru.anchutin.droidquest

class QuestionBank private constructor() {
    private object Holder {
        val INSTANCE = QuestionBank()
    }
    companion object {
        val instance: QuestionBank by lazy {
            Holder.INSTANCE
        }
    }
    var allQuestions = mutableListOf(
        Question(R.string.question_android, true),
        Question(R.string.question_linear, false),
        Question(R.string.question_service, false),
        Question(R.string.question_res, true),
        Question(R.string.question_manifest, true),
        Question(R.string.question_layout, false),
        Question(R.string.question_img, false),
        Question(R.string.question_emu, true),
        Question(R.string.question_mtask, true),
        Question(R.string.question_id, true)
    )
}
