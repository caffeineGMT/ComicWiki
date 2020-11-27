package edu.utcs.comicwiki

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AuthInitActivity : AppCompatActivity() {
    companion object {
        const val RC_SIGNIN = 1
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setTheme(R.style.AppTheme)
                    .build(),
                RC_SIGNIN
            )
        } else {
            val text =
                "You have signed in, please sign out first if you want to sign in as another user."
            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setDisplayNameByEmail(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.d("AuthInitActivity", "XXX, setDisplayNameByEmail current user null")
        } else if (user.displayName == null || user.displayName!!.isEmpty()) {
            user.apply {
                val displayName = this.email?.substringBefore("@")
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()
                if (displayName != null && displayName.isEmpty()) {
                    this.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(javaClass.simpleName, "User profile updated.")
                            }
                            // Now we are done
                            finish()
                        }
                    return false
                } else {
                    Log.d("AuthInitActivity", "XXX displayName $displayName")
                }
            }
        } else {
            Log.d(javaClass.simpleName, "displayName set to ${user.displayName}")
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGNIN) {
            if (resultCode == Activity.RESULT_OK) {
                val text = "You have signed in."
                Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
                if (setDisplayNameByEmail()) {
                    finish()
                }
            } else {
                finish()
            }
        }
    }
}