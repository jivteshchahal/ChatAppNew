package com.example.chatapp.viewModel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.service.model.ContactsModel
import com.example.chatapp.service.repositories.ContactListRepository

class ContactsFragViewModel : ViewModel() {
    lateinit var otherUserNum: String
    private lateinit var contactList: MutableLiveData<List<ContactsModel>>
    private lateinit var contactListRepository: ContactListRepository

    fun init() {
        contactListRepository = ContactListRepository.getInstance()
    }

    fun getContacts(activity: Activity): LiveData<List<ContactsModel>> {
        contactList = contactListRepository.getVolleyData(activity)
        return contactList
    }

}