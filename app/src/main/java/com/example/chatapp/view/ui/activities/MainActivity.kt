package com.example.chatapp.view.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.chatapp.R
import com.example.chatapp.view.adapters.TabAdapter
import com.example.chatapp.view.ui.fragments.ContactsFragment
import com.example.chatapp.view.ui.fragments.ConversationFragment
import com.example.chatapp.view.ui.fragments.SettingsFragment
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager = findViewById<ViewPager>(R.id.fragLayout)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(ContactsFragment(), "Contacts")
        adapter.addFragment(ConversationFragment(), "Chats")
        adapter.addFragment(SettingsFragment(), "Settings")
        viewPager.adapter = adapter
//        tabLayout.selectT())
        tabLayout.setupWithViewPager(viewPager)
        val tab: TabLayout.Tab = tabLayout.getTabAt(1)!!
        tab.select()
    }

}
