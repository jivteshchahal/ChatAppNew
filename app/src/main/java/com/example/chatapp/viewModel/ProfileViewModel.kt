package com.example.chatapp.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.chatapp.service.model.ProfileModel
import com.example.chatapp.service.repositories.ProfileRepository

class ProfileViewModel {
    lateinit var context: Context
    private lateinit var profileRepository: ProfileRepository
    fun init(context: Context) {
        profileRepository = ProfileRepository.getInstance(context)
    }

    fun getImage(resultUri: Uri?): LiveData<String> {
        return profileRepository.saveUserPhoto(resultUri)
    }

    fun getOldData(): LiveData<ProfileModel> {
        return profileRepository.getOldData()
    }
}