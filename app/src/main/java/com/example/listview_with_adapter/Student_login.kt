package com.example.listview_with_adapter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listview_with_adapter.MainActivity.tj
import com.example.listview_with_adapter.databinding.ActivityStudentLoginBinding

class Student_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStudentLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Clear text data when back button is pressed
        onBackPressedDispatcher.addCallback(this) {
            binding.email.setText("")
            binding.password.setText("")
            finish()
        }

        binding.textView6.setOnClickListener {
            startActivity(Intent(this, Student_register::class.java))
            finish()
        }

        binding.button.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if(email.isEmpty() || password.isEmpty())
            {
                binding.email.error = "Please enter email"
                binding.password.error = "Please enter password"
            }
            else
            {
                tj.auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    Toast.makeText(this, "Sign In Successful", Toast.LENGTH_SHORT).show()
                    binding.email.setText("")
                    binding.password.setText("")
                    startActivity(Intent(this, Quiz_Activity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}