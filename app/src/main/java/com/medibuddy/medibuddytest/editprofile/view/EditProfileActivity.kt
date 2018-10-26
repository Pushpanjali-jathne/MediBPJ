package com.medibuddy.medibuddytest.editprofile.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.WindowManager
import android.widget.*
import com.bumptech.glide.Glide
import com.ellipsonic.vultus.Utils.MediaUtilty
import com.ellipsonic.vultus.Utils.Utility
import com.medibuddy.medibuddytest.R
import java.io.*

import com.bumptech.glide.request.RequestOptions
import com.medibuddy.medibuddytest.editprofile.presenter.EditProfilePresenter
import com.medibuddy.medibuddytest.profile.ProfileActivity
import java.text.SimpleDateFormat
import java.util.*


class EditProfileActivity : AppCompatActivity(), EditProfileView {


    private val presenter = EditProfilePresenter(this)

    lateinit var tvUserName: TextInputEditText
    lateinit var tvMobile: TextInputEditText
    lateinit var tvEmail: TextInputEditText
    lateinit var tvHeight: TextInputEditText
    lateinit var tvWieght: TextInputEditText
    lateinit var toolbar: Toolbar
    lateinit var ivChangeProfile: ImageView
    lateinit var ivChangeProfilePic: ImageView
    lateinit var llDateOfBirth: LinearLayout
    lateinit var tvDateOfBirth: TextView
    lateinit var spGender: Spinner
    lateinit var btnSave: Button

    private var userChoosenTask: String? = null
    private var bitmap: Bitmap? = null
    private var imageString: String = ""

    private var clFrom: Calendar? = null
    private val clTo: Calendar? = null

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var ageofPeofile: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        MediaUtilty.checkPermission(this@EditProfileActivity);
        MediaUtilty.checkPermissionCamera(this@EditProfileActivity);

        supportActionBar!!.hide()
        initView()
        eventListners()
    }

    override fun SuccessToast() {

        Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
    }

    override fun AlertInvalidation(message: String) {

        Utility.displayMessageAlert(message, this)
    }

    private fun eventListners() {

        toolbar.setNavigationOnClickListener {

            finish()
        }

        btnSave.setOnClickListener {

            var validCondition = presenter.validationForAll(tvUserName.text.toString(), tvMobile.text.toString(), tvEmail.text.toString(), tvHeight.text.toString(),
                    tvWieght.text.toString(), ageofPeofile, spGender.selectedItem.toString(), imageString)

            if (validCondition) {

                var profilrModel = presenter.setProfileDetails11()

                val intent = Intent(this@EditProfileActivity, ProfileActivity::class.java)
                intent.putExtra("ProfileDetail", profilrModel)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }


        }

        llDateOfBirth.setOnClickListener {
            val c1 = Calendar.getInstance()
            year = c1.get(Calendar.YEAR)
            month = c1.get(Calendar.MONTH)
            day = c1.get(Calendar.DAY_OF_MONTH)

            val mDatePicker1 = DatePickerDialog(this@EditProfileActivity, DatePickerDialog.OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
                /* Your code to get date and time */
                year = selectedyear
                month = selectedmonth
                day = selectedday

                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)

                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                tvDateOfBirth.setText(simpleDateFormat.format(calendar.time))

                ageofPeofile = getAge(year, month, day)
                Log.d("ageofPeofile", ageofPeofile)


            }, year, month, day)
            mDatePicker1.setTitle("Select date")
            mDatePicker1.datePicker.maxDate = System.currentTimeMillis() - 1000
            mDatePicker1.show()
        }



        ivChangeProfilePic.setOnClickListener {

            selectImage();
        }

    }


    private fun initView() {

        tvUserName = findViewById(R.id.tvUserName)
        tvMobile = findViewById(R.id.tvMobile)
        tvEmail = findViewById(R.id.tvEmail)
        tvHeight = findViewById(R.id.tvHeight)
        tvWieght = findViewById(R.id.tvWieght)
        toolbar = findViewById(R.id.toolbar)
        ivChangeProfile = findViewById(R.id.ivChangeProfile)
        ivChangeProfilePic = findViewById(R.id.ivChangeProfilePic)
        llDateOfBirth = findViewById(R.id.llDateOfBirth)
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth)
        spGender = findViewById(R.id.spGender)
        btnSave = findViewById(R.id.btnSave)


    }

    private fun selectImage() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")
        val builder = AlertDialog.Builder(this@EditProfileActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
            val result = MediaUtilty.checkPermission(this@EditProfileActivity)
            val camResult = MediaUtilty.checkPermissionCamera(this@EditProfileActivity)
            if (items[item] == "Take Photo") {
                userChoosenTask = "Take Photo"
                if (camResult)
                    cameraIntent()
            } else if (items[item] == "Choose from Library") {
                userChoosenTask = "Choose from Library"
                if (result)
                    galleryIntent()
            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT//
        startActivityForResult(Intent.createChooser(intent, "Select File"), 123)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {
                onSelectFromGalleryResult(data)
                val FilePath = data!!.data!!.path

            } else if (requestCode == 64) {
                try {
                    onCaptureImageResult(data!!)

                    val projection = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = managedQuery(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, null, null, null)
                    val column_index_data = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToLast()

                } catch (e: Exception) {
                    Log.d("image", "message " + e.message)
                    e.printStackTrace()
                }

            }

        }
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        if (data != null) {

            val imageUri = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                bitmap = resize(bitmap!!, bitmap!!.width / 2, bitmap!!.height / 2)
                imageString = android.util.Base64.encodeToString(getFileDataFromDrawable(bitmap!!), android.util.Base64.NO_WRAP)

                Glide.with(this).asBitmap().load(getFileDataFromDrawable(bitmap!!))
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivChangeProfile);


            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

    }

    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    private fun resize(mImage: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        var image = mImage
        if (maxHeight > 0 && maxWidth > 0) {
            val width = image.width
            val height = image.height
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > ratioBitmap) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
        }
        return image
    }

    private fun onCaptureImageResult(data: Intent) {
        bitmap = data.extras!!.get("data") as Bitmap
        val bytes = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val destination = File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis().toString() + ".jpg")
        Glide.with(this).asBitmap().load(getFileDataFromDrawable(bitmap!!))
                .apply(RequestOptions.circleCropTransform())
                .into(ivChangeProfile);

        imageString = android.util.Base64.encodeToString(getFileDataFromDrawable(bitmap!!), android.util.Base64.NO_WRAP)


        val fo: FileOutputStream

        try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    private fun cameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 64)
    }


    private fun getAge(year: Int, month: Int, day: Int): String {
        val today = Calendar.getInstance()
        clFrom = GregorianCalendar(year, month, day)
        clFrom!!.set(year, month, day)
        var age = today.get(Calendar.YEAR) - clFrom!!.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < clFrom!!.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        val ageInt = age
        return ageInt.toString()
    }

}
