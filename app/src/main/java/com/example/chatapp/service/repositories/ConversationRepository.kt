package com.example.chatapp.service.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.R
import com.example.chatapp.service.model.ConversationModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ConversationRepository {
    private var TAG = "Conversation Fragment"
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var context: Context
    private var conversationList: MutableList<ConversationModel> = ArrayList()
    private var mutableLiveDataList: MutableLiveData<List<ConversationModel>> =
        MutableLiveData<List<ConversationModel>>()

    companion object {
        private lateinit var instance: ConversationRepository
        fun getInstance(): ConversationRepository {
            instance = ConversationRepository()
            return instance
        }
    }

    fun getAllConversations(context: Context): MutableLiveData<List<ConversationModel>> {
        db.collection(context.getString(R.string.dbKeyUser)).document(auth.currentUser!!.phoneNumber.toString())
            .collection("chat").get()
            .addOnSuccessListener { documents ->
                var conversationModel: ConversationModel
                for (document in documents) {
                    db.collection("users").document(document.id)
                        .collection("profile").document(document.id).get()
                        .addOnSuccessListener { document1 ->
                            if (document1.data!!["name"] != null) {
                                conversationModel = ConversationModel(
                                    document.id,
                                    document1.data!!["name"].toString(),
                                    document1.data!!["image_url"].toString(),
                                    ""
                                )
                                conversationList.add(conversationModel)
                            }
                            mutableLiveDataList.value = conversationList
                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Error getting documents: ", exception)
                        }

                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents: ", exception)
            }
        return mutableLiveDataList
    }

}

