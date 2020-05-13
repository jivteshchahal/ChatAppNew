package com.example.chatapp.view.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.service.model.ContactsModel
import com.example.chatapp.view.ui.activities.ChatActivity
import kotlinx.android.synthetic.main.fragment_item.view.*

class ContactsViewAdapter(
    private val mValues: List<ContactsModel>, private val activity: FragmentActivity?
) : RecyclerView.Adapter<ContactsViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactsBean = mValues[position]
//        holder.mIdView.text = mValues?.get(position)?.id
        holder.tvName.text = contactsBean.name
//        SaveProfileImage(activity!!.applicationContext).getOldData(holder.imgProfile, edtName)
        if (contactsBean.img_url.isNotEmpty()) {
            Glide.with(activity!!).load(contactsBean.img_url).into(holder.imgProfile)
        } else {
            holder.imgProfile.visibility = View.INVISIBLE
        }
        holder.contactLayout.setOnClickListener {
//            if (s.equals("contacts")){
//                activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.linear,ContactDetailsFragment(mValues!!.get(position)))?.commit()
//            }else {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(
                activity?.getString(R.string.intentChatOtherUserNum),
                contactsBean.phoneNumber
            )
            intent.putExtra(
                activity?.getString(R.string.intentChatOtherUserName),
                contactsBean.name
            )
            activity?.startActivity(intent)
//            }
        }
        with(holder.mView) {
            tag = contactsBean.phoneNumber
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        //        val mIdView: TextView = mView.item_number
        val tvName: TextView = mView.tvNameCon
        val imgProfile: ImageView = mView.imgProfileCon
        val contactLayout: LinearLayoutCompat = mView.contactLayoutItem
        val lastChat: TextView = mView.tvChatCon
        override fun toString(): String {
            return super.toString() + " '" + tvName.text + "'"
        }
    }
}
