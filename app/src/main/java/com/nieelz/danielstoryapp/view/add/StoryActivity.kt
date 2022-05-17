package com.nieelz.danielstoryapp.view.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.nieelz.danielstoryapp.database.local.user.UserLogin
import com.nieelz.danielstoryapp.database.local.user.UserPreferences
import com.nieelz.danielstoryapp.database.remote.retrofit.ApiConfig
import com.nieelz.danielstoryapp.databinding.ActivityStoryBinding
import com.nieelz.danielstoryapp.repo.StoryRepository
import com.nieelz.danielstoryapp.view.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private val storyViewModel: StoryViewModel by viewModels { ViewModelFactory(this) }

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Post Story"
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        storyViewModel.getLocalUser()

        storyViewModel.user.observe(this){ token = it.token }

        binding.buttonCamera.setOnClickListener { startCamera() }
        binding.buttonGallery.setOnClickListener { startGallery() }
        binding.buttonUpload.setOnClickListener { startUpload() }
    }


    private fun startUpload() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)


            val description = binding.descTextInput.text.toString()
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            storyViewModel.postStory(token, imageMultipart, description) { isSuccess, message ->
                if (isSuccess) {
                    setResult(RESULT_OK)
                    finish()
                }
                Toast.makeText(
                    this,
                    if (isSuccess) "Cerita berhasil diposting" else message, Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            Toast.makeText(
                this@StoryActivity,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }




    //Camera Intent
    private var getFile: File? = null
    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@StoryActivity,
                "com.nieelz.danielstoryapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            val result = reduceFileImage(myFile)
            getFile = result

//            val result2 = rotateBitmap(
//                BitmapFactory.decodeFile(myFile.path),
//                true
//            )

            binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(result.path))
        }
    }



    //gallery
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@StoryActivity)
            getFile = myFile
            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }




    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }






}