package com.example.bolo_davaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var goBackButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var resetButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        auth = FirebaseAuth.getInstance()
        emailEditText = findViewById(R.id.regEmailEditText)
        resetButton = findViewById(R.id.resetButton)
        goBackButton = findViewById(R.id.goBackButton)

        resetButton.setOnClickListener {
            val email = emailEditText.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "შეიყვანეთ ელფოსტა", Toast.LENGTH_LONG).show()
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "შეცდომა", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        goBackButton.setOnClickListener {
            finish()
        }
    }
}