package com.example.chatapp.service.repositories

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.service.model.ProfileModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.IOException

class ProfileRepository {
    private var imgUrl: String = ""

    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val phoneNumber = firebaseUser!!.phoneNumber.toString()

    companion object {
        private lateinit var instance: ProfileRepository
        private lateinit var context: Context
        fun getInstance(context: Context): ProfileRepository {
            instance = ProfileRepository()
            this.context = context
            return instance
        }
    }

    fun saveUserPhoto(resultUri: Uri?): MutableLiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        if (resultUri != null) {
            val filepath: StorageReference =
                FirebaseStorage.getInstance().reference.child("profileImages")
                    .child(firebaseUser!!.uid)
            var bitmap: Bitmap? = null
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, resultUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val baos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, baos)
            filepath.putFile(resultUri).addOnSuccessListener(OnSuccessListener<Any?> { })
                .addOnFailureListener(OnFailureListener { })
            filepath.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                val firebaseAuth = FirebaseAuth.getInstance()
                val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                val userProfile: MutableMap<String, Any> =
                    java.util.HashMap()
                userProfile["image_url"] = uri.toString()
                imgUrl = uri.toString()
                mutableLiveData.value = imgUrl
                db.collection("users").document(phoneNumber)
                    .collection("profile")
                    .document(phoneNumber)
                    .update(userProfile)
                    .addOnCompleteListener {
//                        imageView.setImageURI(resultUri)
                    }.addOnFailureListener { e ->

                        db.collection("users").document(phoneNumber)
                            .collection("profile")
                            .document(phoneNumber).set(userProfile)
                            .addOnCompleteListener {
//                                imageView.setImageURI(resultUri)
                            }.addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Please Re Upload Image",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
            })
        }
        return mutableLiveData
    }

    fun getOldData(): MutableLiveData<ProfileModel> {
        val mutableLiveData: MutableLiveData<ProfileModel> =
            MutableLiveData<ProfileModel>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("users").document(firebaseUser!!.phoneNumber.toString())
            .collection("profile")
            .document(firebaseUser.phoneNumber.toString()).get().addOnSuccessListener {
                if (it.exists() && it.data!!["name"] != null) {
                    imgUrl = it.data!!["image_url"].toString()
                    mutableLiveData.value =
                        ProfileModel(it.data!!["name"].toString(), imgUrl, "Not Available")
                }
            }
        return mutableLiveData
    }
}