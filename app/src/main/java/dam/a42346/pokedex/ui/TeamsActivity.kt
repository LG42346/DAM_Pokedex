package dam.a42346.pokedex.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dam.a42346.pokedex.R

class TeamsActivity : BottomNavActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        auth = Firebase.auth

        val layout = findViewById<LinearLayout>(R.id.programmatic_layout)

        val emailEditText = EditText(this).apply {
            hint = "Email"
        }

        val passwordEditText = EditText(this).apply {
            hint = "Password"
        }

        // Create a login Button
        val loginButton = Button(this).apply {
            text = "Login"
            setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@TeamsActivity) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                baseContext, "Authentication succeeded.",
                                Toast.LENGTH_SHORT
                            ).show()
                            // TODO: Navigate to the next activity
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        // Add the EditText fields and Button to the LinearLayout
        layout.addView(emailEditText)
        layout.addView(passwordEditText)
        layout.addView(loginButton)
    }

    override val contentViewId: Int
        get() = R.layout.activity_teams
    override val navigationMenuItemId: Int
        get() = R.id.navigation_teams
}