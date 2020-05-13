package com.example.chatapp.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.viewModel.ChatImgShowViewModel

class ChatImgShowFrag : Fragment() {

    companion object {
        fun newInstance() = ChatImgShowFrag()
    }

    private lateinit var viewModel: ChatImgShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_img_show_fragment, container, false)
        val imgChat = view.findViewById<ImageView>(R.id.chatImg)
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.chat_container, ChatsFragment()).commit()
        }
        Glide.with(activity!!).load(arguments?.get("profile_img")).into(imgChat)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatImgShowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
