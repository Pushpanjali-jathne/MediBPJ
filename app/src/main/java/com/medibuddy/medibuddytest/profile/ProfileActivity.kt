package com.medibuddy.medibuddytest.profile

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.medibuddy.medibuddytest.R
import com.medibuddy.medibuddytest.editprofile.model.EditProfileModel
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ellipsonic.vultus.Utils.Utility
import com.medibuddy.medibuddytest.editprofile.view.EditProfileActivity


class ProfileActivity : AppCompatActivity() {


    lateinit var profileModel: EditProfileModel

    private lateinit var ivChangeProfile: ImageView
    private lateinit var ivEditProfile: ImageView
    lateinit var toolbar: Toolbar

    private lateinit var tvHeight: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvAgeGender: TextView
    private lateinit var tvWieght: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar!!.hide()
        initView()
        eventListners()
        updateProfileDetails()
    }


    private fun eventListners() {

        ivEditProfile.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

    }


    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        ivChangeProfile = findViewById(R.id.ivChangeProfile)
        ivEditProfile = findViewById(R.id.ivEditProfile)
        tvHeight = findViewById(R.id.tvHeight)
        tvEmail = findViewById(R.id.tvEmail)
        tvUserName = findViewById(R.id.tvUserName)
        tvPhone = findViewById(R.id.tvPhone)
        tvAgeGender = findViewById(R.id.tvAgeGender)
        tvWieght = findViewById(R.id.tvWieght)
        Utility.centerToolbarTitle(toolbar)
    }

    fun updateProfileDetails() {

        try {

            profileModel = intent.extras.getParcelable("ProfileDetail")
            var imgByptes = android.util.Base64.decode(profileModel.imageByteArray, 0)


            if (profileModel.imageByteArray.toString().length > 0) {

                Glide.with(this).asBitmap().load(imgByptes)
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivChangeProfile)
            } else {

                Glide.with(this).load(R.drawable.placeholder)
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivChangeProfile)
            }

            tvHeight.text = profileModel.height + "cm"
            tvEmail.text = profileModel.email
            tvUserName.text = profileModel.name
            tvPhone.text = profileModel.mobileNo
            tvAgeGender.text = profileModel.dateOfBirth + "," + profileModel.gender
            tvWieght.text = profileModel.weight + "kg"

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
