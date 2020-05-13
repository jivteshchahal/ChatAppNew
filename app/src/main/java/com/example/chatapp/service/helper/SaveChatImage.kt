package com.example.chatapp.service.helper

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.IOException

class SaveChatImage(private val context: Context) {
    private lateinit var imgUrl: String
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val phoneNumber = firebaseUser!!.phoneNumber.toString()
    private var randomString: String = "null1"
    fun saveChatPhoto(resultUri: Uri?): String {
        if (resultUri != null) {
            val filepath: StorageReference =
                FirebaseStorage.getInstance().reference.child("chatImage").child(randomString)
            var bitmap: Bitmap? = null
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, resultUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val baos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, baos)
            filepath.putFile(resultUri).addOnSuccessListener(OnSuccessListener<Any?> {
//                    task.setResult("alaksaksjxn"+Math.random().toInt()+Math.random().toInt()+Math.random().toInt());
            }).addOnFailureListener(OnFailureListener {
            })
        }
        return randomString
    }
}