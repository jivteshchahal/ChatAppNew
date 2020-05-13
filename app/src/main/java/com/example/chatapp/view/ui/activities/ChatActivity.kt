package com.example.chatapp.view.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.view.ui.fragments.ChatsFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChatActivity : AppCompatActivity() {
    private lateinit var fragment: Fragment
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        db = FirebaseFirestore.getInstance()
        fragment = ChatsFragment()
        val user: MutableMap<String, Any> = HashMap()
        var hour = Calendar.getInstance()[Calendar.HOUR]
        val min = Calendar.getInstance()[Calendar.MINUTE]
        val pm = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        val noon: String
        noon = if (pm > 11) {
            " pm"
        } else if (pm == 12) {
            hour = 12
            " pm"
        } else {
            " am"
        }
//        checkNewMSG()
        user["lastSeen"] = "$hour:$min$noon"

        db.collection("users").document(firebaseUser.phoneNumber.toString())
            .collection("profile").document(
                firebaseUser.phoneNumber.toString()
            )
            .update(user)
            .addOnCompleteListener { task: Task<Void> ->
                if (task.isComplete) {
//                    startActivity(
//                        Intent(
//                            this,
//                            MainActivity::class.java
//                        )
//
//                    )
//                    finish()
                }
            }
            .addOnFailureListener { e ->
//                db.collection("users")
//                    .document(firebaseUser.phoneNumber.toString())
//                    .collection("profile")
//                    .document(firebaseUser.phoneNumber.toString())
//                    .set(user)
//                    .addOnCompleteListener { task: Task<Void> ->
//                        if (task.isComplete) {
//                            startActivity(
//                                Intent(
//                                    this,
//                                    MainActivity::class.java
//                                )
//                            )
//                        }
//                    }
//                    .addOnFailureListener { e ->
//                        Log.w(
//                            "abc",
//                            "Error adding document",
//                            e
//                        )
//                    }
            }
        supportFragmentManager.beginTransaction().replace(R.id.chat_container, fragment)
            .commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
//        startActivity(Intent(this,MainActivity::class.java))
    }
}