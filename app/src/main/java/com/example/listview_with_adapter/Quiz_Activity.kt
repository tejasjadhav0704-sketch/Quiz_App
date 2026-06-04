package com.example.listview_with_adapter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listview_with_adapter.MainActivity.tj
import com.example.listview_with_adapter.databinding.ActivityQuizBinding

class Quiz_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding

    val quiz = arrayListOf<Quiz>()
    var selectedOptions = ""
    var currentIndex = 0
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Handle back press
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        FetchData()

        binding.listview.setOnItemClickListener { parent, view, position, id ->
            selectedOptions = binding.listview.getItemAtPosition(position).toString()
        }

        binding.button2.setOnClickListener {
            if (selectedOptions.isEmpty()) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val correctAns = quiz[currentIndex].correctAns
            if (selectedOptions == correctAns) {
                score++
            }

            selectedOptions = ""
            currentIndex++

            if (currentIndex < quiz.size)
            {
                showQuestionOptions()
            } else
            {
                binding.textView11.text = "Quiz Finished 🎉"
                binding.textView12.text = "Score: $score / ${quiz.size}"
                binding.listview.adapter = null
                binding.button2.isEnabled = false
            }
        }
    }

    private fun FetchData() {
        tj.database.collection("Questions")
            .get()
            .addOnSuccessListener { documents ->
                quiz.clear()
                for (document in documents) {
                    val dataFetched = document.toObject(Quiz::class.java)
                    quiz.add(dataFetched)
                }

                if (quiz.isNotEmpty()) {
                    currentIndex = 0 // Start from the first question
                    showQuestionOptions()
                } else {
                    Toast.makeText(this, "No Questions Found in Database", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun showQuestionOptions() {
        if (quiz.isNotEmpty() && currentIndex < quiz.size) {
            val currentQuiz = quiz[currentIndex]
            binding.textView12.text = currentQuiz.qts

            val options = listOf(
                currentQuiz.option1,
                currentQuiz.option2,
                currentQuiz.option3,
                currentQuiz.option4
            )

            val adaptermaking = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, options)
            binding.listview.adapter = adaptermaking
            binding.listview.choiceMode = ListView.CHOICE_MODE_SINGLE
            binding.listview.clearChoices()
        }
    }
}
