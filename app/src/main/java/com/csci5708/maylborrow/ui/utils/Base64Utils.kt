package com.csci5708.maylborrow.ui.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


/**
 * Utility class to convert image to base 64 and vice versa
 *
 * Reference : http://www.java2s.com/example/android/file-input-output/file-uri-to-base64.html
 */
class Base64Utils {
    companion object{

        public fun uriToBitmap(uri: Uri?) : Bitmap{
            val imageBytes = Base64.decode(uri.toString(), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.count())
            return decodedImage
        }

        public fun uriToBitmap_dirctURI(uri: Uri?, context: Context): Bitmap{
            val bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri)
            return bitmap
        }

        public fun imageToBase64(uri: Uri?, contentResolver: ContentResolver): String
        {
            var encodedBase64 = ""

            try {
                val bytes: ByteArray? = readBytes(uri, contentResolver)
                encodedBase64 = Base64.encodeToString(bytes, 0)
            }
            catch (e: IOException)
            {
                e.printStackTrace()
            }

            return encodedBase64
        }

        @Throws(IOException::class)
        private fun readBytes(uri: Uri?, resolver: ContentResolver): ByteArray? {
            // this dynamically extends to take the bytes you read
            val inputStream: InputStream? = uri?.let { resolver.openInputStream(it) }
            val byteBuffer = ByteArrayOutputStream()

            // this is storage overwritten on each iteration with bytes
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)

            // we need to know how may bytes were read to write them to the
            // byteBuffer
            var len = 0
            if (inputStream != null) {
                while (inputStream.read(buffer).also { len = it } != -1) {
                    byteBuffer.write(buffer, 0, len)
                }
            }

            // and then we can return your byte array.
            return byteBuffer.toByteArray()
        }
    }
}