package com.example.listview_with_adapter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listview_with_adapter.MainActivity.tj
import com.example.listview_with_adapter.databinding.ActivityTeacherDashboardBinding
import com.example.listview_with_adapter.databinding.ActivityTeacherRegisterBinding

class Teacher_dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTeacherDashboardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.button3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.button.setOnClickListener {
            val qts = binding.qtsEdittext.text.toString()
            val op1 = binding.option1.text.toString()
            val op2 = binding.option2.text.toString()
            val op3 = binding.option3.text.toString()
            val op4 = binding.option4.text.toString()
            val corAns = binding.correctAns.text.toString()
            
            if (qts.isEmpty() || op1.isEmpty() || op2.isEmpty() || op3.isEmpty() || op4.isEmpty() || corAns.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val setValue = Quiz(qts, op1, op2, op3, op4, corAns)

            tj.database.collection("Questions")
                .add(setValue).addOnSuccessListener {
                    Toast.makeText(this, "Question Added Successfully !!!", Toast.LENGTH_SHORT).show()
                    // Clear fields after success
                    binding.qtsEdittext.setText("")
                    binding.option1.setText("")
                    binding.option2.setText("")
                    binding.option3.setText("")
                    binding.option4.setText("")
                    binding.correctAns.setText("")
                }.addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
