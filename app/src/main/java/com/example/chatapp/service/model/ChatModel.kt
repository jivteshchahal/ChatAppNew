package com.example.chatapp.service.model

data class ChatModel(
    val chatMessage: String,
    val chatImage: String,
    val chatVideo: String,
    val chatVoiceNote: String,
    val chatLatLong: String,
    val timestamp: String,
    val user: String
)