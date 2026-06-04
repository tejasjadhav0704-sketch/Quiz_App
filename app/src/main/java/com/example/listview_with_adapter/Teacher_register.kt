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
import com.example.listview_with_adapter.databinding.ActivityTeacherLoginBinding
import com.example.listview_with_adapter.databinding.ActivityTeacherRegisterBinding

class Teacher_register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTeacherRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this){
            binding.email.setText("")
            binding.password.setText("")
            finish()
        }

        binding.textView6.setOnClickListener {
            startActivity(Intent(this, Teacher_login::class.java))
            finish()
        }

        binding.button.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
            {
                binding.email.error = "Please enter email"
                binding.password.error = "Please enter password"
                binding.confirmPassword.error = "Please enter confirm password"
            }
            else if(password != confirmPassword)
            {
                binding.confirmPassword.error = "Password does not match"
            }
            else
            {
                tj.auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    binding.email.setText("")
                    binding.password.setText("")
                    binding.confirmPassword.setText("")
                    Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Teacher_login::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}