package com.example.projemanag.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.projemanag.R
import com.example.projemanag.activities.CreateBoardActivity
import com.example.projemanag.activities.MyProfileActivity
import java.io.IOException

object Constants {

    // Firebase Constants
    // This  is used for the collection name for USERS.
    const val USERS: String = "users"

    // This  is used for the collection name for USERS.
    const val BOARDS: String = "boards"

    // Firebase database field names
    const val IMAGE: String = "image"
    const val NAME: String = "name"
    const val MOBILE: String = "mobile"
    const val ASSIGNED_TO: String = "assignedTo"
    const val DOCUMENT_ID: String = "documentId"
    const val TASK_LIST: String = "taskList"
    const val ID: String = "id"
    const val EMAIL: String = "email"

    const val BOARD_DETAIL: String = "board_detail"

    const val TASK_LIST_ITEM_POSITION: String = "task_list_item_position"
    const val CARD_LIST_ITEM_POSITION: String = "card_list_item_position"

    const val BOARD_MEMBERS_LIST: String = "board_members_list"

    const val SELECT: String = "Select"
    const val UN_SELECT: String = "UnSelect"

    //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult
    const val READ_STORAGE_PERMISSION_CODE = 1
    // A unique code of image selection from Phone Storage.
    const val PICK_IMAGE_REQUEST_CODE = 2

    const val PROGEMANAG_PREFERENCES: String = "ProjemanagPrefs"
    const val FCM_TOKEN:String = "fcmToken"
    const val FCM_TOKEN_UPDATED:String = "fcmTokenUpdated"

    // TODO (Step 1: Add the base url  and key params for sending firebase notification.)
    // START
    const val FCM_BASE_URL:String = "https://fcm.googleapis.com/fcm/send"
    const val FCM_AUTHORIZATION:String = "authorization"
    const val FCM_KEY:String = "key"
    const val FCM_SERVER_KEY:String = "AAAATStfJ-k:APA91bEE-NhRF7PLBh7QEYkWUaHL5sycmzC0Eddaxviyocd7EcI9t2PMUN6_FdcEqUOxjsu45qSnR8TnDw__nKuAbK9OOiQLVyVD4hhjUz1IzI9ODs6Hm58lEXC7CISUyXjONWcGyJX1"
    const val FCM_KEY_TITLE:String = "title"
    const val FCM_KEY_MESSAGE:String = "message"
    const val FCM_KEY_DATA:String = "data"
    const val FCM_KEY_TO:String = "to"
    // END
    const val READ_STORAGE_PERMISSION = 1

    fun showImageChooser(activity: Activity){
        if(isReadStoragePermissionGranted(activity)){
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            when(activity){
                is MyProfileActivity ->{
                    activity.profileOpenGalleryLauncher.launch(galleryIntent)
                }
                is CreateBoardActivity ->{
                    activity.boardOpenGalleryLauncher.launch(galleryIntent)
                }
            }
        }else{
            requestPermission(activity, READ_STORAGE_PERMISSION)
        }
    }


    private fun isReadStoragePermissionGranted(activity: Activity): Boolean{
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission(activity: Activity, permissionCode: Int){
        val permissionsRequest: MutableList<String> = ArrayList()

        when(permissionCode){
            READ_STORAGE_PERMISSION -> {
                permissionsRequest.add( Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        if (permissionsRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(activity,
                permissionsRequest.toTypedArray(), permissionCode)
        }
    }


    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}