package com.example.chatapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.service.model.ConversationModel
import com.example.chatapp.service.repositories.ConversationRepository

class ConversationFragViewModel : ViewModel() {
    private lateinit var context:Context
    lateinit var otherUserNum: String
    private lateinit var conversationList: MutableLiveData<List<ConversationModel>>
    private lateinit var contactListRepository: ConversationRepository
    fun init(context: Context) {
        this.context = context
        contactListRepository = ConversationRepository.getInstance()
    }

    fun getConversation(): LiveData<List<ConversationModel>> {
        conversationList = contactListRepository.getAllConversations(context)
        return conversationList
    }
}