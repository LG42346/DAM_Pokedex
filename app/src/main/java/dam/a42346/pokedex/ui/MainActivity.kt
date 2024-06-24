package dam.a42346.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dam.a42346.pokedex.R

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val PREFS_NAME = "FirebaseAuthPrefs"
    private val AUTHENTICATED_KEY = "authenticated"

    private fun firebaseAnonAuth(){
        auth = Firebase.auth
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Anonymous login succeeded.", Toast.LENGTH_SHORT).show()
                    Log.d("Firebase", "Anonymous login succeeded.")

                    Toast.makeText(this, "Firebase UID: ${auth.currentUser?.uid}", Toast.LENGTH_SHORT).show()
                    Log.d("Firebase", "Firebase UID: ${auth.currentUser?.uid}")
                } else {
                    Toast.makeText(this, "Anonymous login failed.", Toast.LENGTH_SHORT).show()
                    Log.d("Firebase", "Anonymous login failed.")
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Firebase.database.setPersistenceEnabled(true) //important

        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val hasAuthenticatedBefore = sharedPreferences.getBoolean(AUTHENTICATED_KEY, false)
        if (!hasAuthenticatedBefore) {
            firebaseAnonAuth()
            sharedPreferences.edit().putBoolean(AUTHENTICATED_KEY, true).apply()
        }
        else {
            Toast.makeText(this, "Already authenticated.", Toast.LENGTH_SHORT).show()
            Log.d("Firebase", "Already authenticated.")
        }

        startActivity(Intent(this, RegionsActivity::class.java))
        //startActivity(Intent(this, TeamsActivity::class.java))
    }
}

