package com.example.deeplinkstarter.ui.profile

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.deeplinkstarter.R
import com.example.deeplinkstarter.databinding.ActivityProfileBinding
import com.example.deeplinkstarter.enum.AppParametersEnum

class ProfileActivity : AppCompatActivity() {

    val url:String = "https://www.isc.co.ir/portal/home/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutInflater = layoutInflater
        val profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        val profileRoot = profileBinding.root
        val username = intent.getStringExtra(AppParametersEnum.USER_NAME.value)
        Glide.with(this)
            .load("https://github.com/${username}.png?size=40")
            .fitCenter()
            .apply( RequestOptions().override(48, 48))
            .into(profileBinding.imageView2);
        profileBinding.internetExplorer.setOnClickListener({
            val webpage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        })
        profileBinding.openMap.setOnClickListener({
            val intent = Intent(ACTION_VIEW).apply {
                data = Uri.parse("geo:35.76, 51.43")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        })

        setContentView(profileRoot)
    }
}