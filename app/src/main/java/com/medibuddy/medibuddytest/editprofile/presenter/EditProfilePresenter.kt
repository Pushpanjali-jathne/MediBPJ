package com.medibuddy.medibuddytest.editprofile.presenter

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.medibuddy.medibuddytest.editprofile.view.EditProfileView
import com.medibuddy.medibuddytest.editprofile.model.EditProfileModel
import com.medibuddy.medibuddytest.editprofile.view.EditProfileActivity
import com.medibuddy.medibuddytest.profile.ProfileActivity

class EditProfilePresenter(var editProfileView: EditProfileView) {

    var profilrModel = EditProfileModel()


    fun validationForAll(username: String, mobileNumber: String, emailId: String, height: String, weight: String, dateOfBirth: String, spinnerValue: String, imageString: String): Boolean {

        if (username.length <= 0) {

            editProfileView.AlertInvalidation("Please enter name")
            return false

        } else if (dateOfBirth.length <= 0) {

            editProfileView.AlertInvalidation("Please enter Date of Birth")
            return false

        } else if (spinnerValue.length <= 0) {

            editProfileView.AlertInvalidation("Please enter Date of Birth")
            return false


        } else if (emailId.length <= 0
                || !(emailId.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+".toRegex()))) {

            editProfileView.AlertInvalidation("Please enter valid email")
            return false


        } else if (mobileNumber.length <= 0 || mobileNumber.length > 8) {


            editProfileView.AlertInvalidation("Please enter valid mobile number")
            return false

        } else if (height.length <= 0) {

            editProfileView.AlertInvalidation("Please enter hieght")
            return false

        } else if (weight.length <= 0) {

            editProfileView.AlertInvalidation("Please enter wieght")
            return false

        }
        else{

            profilrModel.imageByteArray = imageString
            profilrModel.name = username
            profilrModel.mobileNo = mobileNumber
            profilrModel.email = emailId
            profilrModel.height = height
            profilrModel.weight =
                    weight
            profilrModel.dateOfBirth = dateOfBirth
            profilrModel.gender = spinnerValue
            editProfileView.SuccessToast()


            return true
        }
        return false

    }

    fun setProfileDetails11():EditProfileModel {

        return profilrModel
    }






}