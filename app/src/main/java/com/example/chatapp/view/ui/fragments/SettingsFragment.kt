package com.example.chatapp.view.ui.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chatapp.R
import com.example.chatapp.service.model.ProfileModel
import com.example.chatapp.view.ui.activities.LoginActivity
import com.example.chatapp.viewModel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private lateinit var imgUrl: String
    private lateinit var imageUri: Uri
    private lateinit var imgProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        initView(view)
        profileViewModel = ProfileViewModel()
        profileViewModel.init(activity!!.applicationContext)
        getDataInView(tvName, imgProfile)
        imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        val btnSignOut = view.findViewById<Button>(R.id.btnSignOut)
        btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
        return view
    }

    private fun initView(view: View) {
        imgProfile = view.findViewById(R.id.imgProfileSetting)
        tvName = view.findViewById<TextView>(R.id.tvNameSet)
    }

    @SuppressLint("CheckResult")
    private fun getDataInView(edtName: TextView, imageView: ImageView) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.load)
        requestOptions.error(R.drawable.ic_launcher_foreground)
        val profileData: LiveData<ProfileModel> = profileViewModel.getOldData()
        profileData.observe(activity!!, Observer {
            if (it != null && activity != null) {
                edtName.text = it.name
                imgUrl = it.imageUrl
                Glide.with(activity!!).load(it.imageUrl).apply(requestOptions).into(imageView)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // If this was started from a Fragment we've already checked the upper 16 bits were not in
        // use, and then repurposed them for the Fragment's index.
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            imageUri = data!!.data!!
            profileViewModel.getImage(imageUri).observe(this,
                Observer {
                    imgUrl = it
                    Glide.with(this).load(imgUrl).into(imgProfile)
                })
        }
    }
}
