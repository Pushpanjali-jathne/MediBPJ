package com.medibuddy.medibuddytest.editprofile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EditProfileModel implements Parcelable {

    protected EditProfileModel(Parcel in) {
        mobileNo = in.readString();
        email = in.readString();
        height = in.readString();
        weight = in.readString();
        dateOfBirth = in.readString();
        gender = in.readString();
        imageByteArray = in.readString();
        name = in.readString();
    }

    public EditProfileModel(){


    }

    public static final Creator<EditProfileModel> CREATOR = new Creator<EditProfileModel>() {
        @Override
        public EditProfileModel createFromParcel(Parcel in) {
            return new EditProfileModel(in);
        }

        @Override
        public EditProfileModel[] newArray(int size) {
            return new EditProfileModel[size];
        }
    };

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(String imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String mobileNo ;
    private String email ;
    private String height ;
    private String weight ;
    private String dateOfBirth ;
    private String gender ;
    private String imageByteArray ;
    private String name ;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileNo);
        dest.writeString(email);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(dateOfBirth);
        dest.writeString(gender);
        dest.writeString(imageByteArray);
        dest.writeString(name);
    }
}

