package com.example.chatapp.view.adapters


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chatapp.R
import com.example.chatapp.service.model.ChatModel
import com.example.chatapp.view.ui.fragments.ChatImgShowFrag

class ChatRecyclerViewAdapter(
    private val mValues: List<ChatModel>?,
    private val flag: String,
    private val activity: FragmentActivity
) :
    RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>() {
    private val requestOptions = RequestOptions()

    @SuppressLint("CheckResult")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        requestOptions.placeholder(R.drawable.load)
        requestOptions.error(R.drawable.ic_launcher_foreground)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.layoutCurrent.visibility = View.INVISIBLE
        holder.layoutOther.visibility = View.INVISIBLE
        holder.tvMsgCurrent.visibility = View.GONE
        holder.tvMsgOther.visibility = View.GONE
        holder.imgChatCurrent.visibility = View.GONE
        holder.tvTimeOther.visibility = View.GONE
        holder.imgChatOther.visibility = View.GONE
        if (mValues?.get(position)?.user.equals("one")) {
            if (mValues?.get(position)?.chatMessage != "") {
                holder.tvMsgCurrent.visibility = View.VISIBLE
                holder.tvMsgCurrent.text = mValues?.get(position)!!.chatMessage
                holder.tvTimeCurrent.text = mValues[position].timestamp
                holder.layoutCurrent.visibility = View.VISIBLE
            } else if (mValues[position].chatImage.isNotEmpty()) {
                holder.imgChatCurrent.visibility = View.VISIBLE
                Glide.with(holder.mView.context).load(mValues[position].chatImage)
                    .apply(requestOptions).into(holder.imgChatCurrent)
                holder.tvTimeCurrent.text = mValues[position].timestamp
                holder.layoutCurrent.visibility = View.VISIBLE
                holder.imgChatCurrent.setOnClickListener {
                    val bundle = Bundle()
                    if (mValues[position].chatImage.isNotEmpty()) {
                        bundle.putString("profile_img", mValues[position].chatImage)
                        val fragment = ChatImgShowFrag.newInstance()
                        fragment.arguments = bundle
                        activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.chat_container, fragment).commit()
                    }
                }
            } else if (mValues[position].chatVideo.isNotEmpty()) {
//                Glide.with(holder.mView.context).load(mValues[position].chatVideo).into(holder.imgChatCurrent )
                holder.tvTimeCurrent.text = mValues[position].timestamp
                holder.layoutCurrent.visibility = View.VISIBLE
            }

        } else if (mValues?.get(position)?.user.equals("two")) {
            when {
                mValues?.get(position)?.chatMessage!! != "" -> {
                    holder.tvTimeOther.visibility = View.VISIBLE
                    holder.tvMsgOther.text = mValues[position].chatMessage
                    holder.tvMsgOther.visibility = View.VISIBLE
                    holder.layoutOther.visibility = View.VISIBLE
                    holder.tvTimeOther.text = mValues[position].timestamp
                }
                mValues[position].chatImage.isNotEmpty() -> {
                    Glide.with(holder.mView.context).load(mValues[position].chatImage)
                        .apply(requestOptions).into(holder.imgChatOther)
                    holder.tvTimeOther.visibility = View.VISIBLE
                    holder.tvTimeOther.text = mValues[position].timestamp
                    holder.imgChatOther.visibility = View.VISIBLE
                    holder.layoutOther.visibility = View.VISIBLE
                    holder.imgChatOther.setOnClickListener {
                        val bundle = Bundle()
                        if (mValues[position].chatImage.isNotEmpty()) {
                            bundle.putString("profile_img", mValues[position].chatImage)
                            val fragment = ChatImgShowFrag.newInstance()
                            fragment.arguments = bundle
                            activity.supportFragmentManager.beginTransaction()
                                .replace(R.id.chat_container, fragment).commit()
                        }
                    }
                }
                mValues[position].chatVideo.isNotEmpty() -> {
                    Glide.with(holder.mView.context).load(mValues[position].chatVideo)
                        .into(holder.imgChatOther)
                    holder.tvTimeOther.visibility = View.VISIBLE
                    holder.tvTimeOther.text = mValues[position].timestamp
                    holder.layoutOther.visibility = View.VISIBLE
                }
            }
        }

        with(holder.mView) {
            tag = mValues!![position].timestamp
        }
    }

    override fun getItemCount(): Int = mValues!!.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvMsgCurrent: TextView = mView.findViewById(R.id.tvMessageCurrentUser)
        val tvTimeCurrent: TextView = mView.findViewById(R.id.tvCurrentTime)
        val tvMsgOther: TextView = mView.findViewById(R.id.tvMessageOtherUser)
        val tvTimeOther: TextView = mView.findViewById(R.id.tvOtherTime)
        val layoutOther: LinearLayout = mView.findViewById(R.id.lLayoutOther)
        val layoutCurrent: LinearLayout = mView.findViewById(R.id.lLayoutCurrent)
        val imgChatCurrent: ImageView = mView.findViewById(R.id.imgCurrentMsgChat)
        val imgChatOther: ImageView = mView.findViewById(R.id.imgOtherMsgChat)
    }
}
