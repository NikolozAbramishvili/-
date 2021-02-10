package com.example.bolo_davaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_update_password.*


class UpdatePasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var goBackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        auth = FirebaseAuth.getInstance()
        goBackButton = findViewById(R.id.goBackToMainButton)

        goBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        changePasswordButton.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword() {

        if (currentPwdEditText.text!!.isNotEmpty() &&
                newPwdEditText.text!!.isNotEmpty() &&
                confirmPwdEditText.text!!.isNotEmpty()
        ) {

            if (newPwdEditText.text.toString().equals(confirmPwdEditText.text.toString())) {

                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                            .getCredential(user.email!!, currentPwdEditText.text.toString())

// Prompt the user to re-provide their sign-in credentials
                    user?.reauthenticate(credential)
                            ?.addOnCompleteListener {
                                if (it.isSuccessful) {
                                    user?.updatePassword(newPwdEditText.text.toString())
                                            ?.addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    Toast.makeText(this, "პაროლი შეიცვალა.", Toast.LENGTH_SHORT).show()
                                                    auth.signOut()
                                                    startActivity(Intent(this, LoginActivity::class.java))
                                                    finish()
                                                }
                                            }

                                } else {
                                    Toast.makeText(this, "პაროლი არასწორია.", Toast.LENGTH_SHORT).show()
                                }
                            }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

            } else {
                Toast.makeText(this, "პაროლები არ ემთხვევა.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "შეავსეთ ყველა ველი.", Toast.LENGTH_SHORT).show()
        }
    }
}

