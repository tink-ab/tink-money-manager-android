package com.tink.moneymanagerui.util

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.annotation.AttrRes
import com.tink.moneymanagerui.overview.accounts.setImageResFromAttr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ImageUtils {

    /**
     * Use this to avoid adding a dependency
     */
    fun loadImageFromUrl(imageView: ImageView, url: String, @AttrRes defaultImage: Int) {
        GlobalScope.launch {
            try {
                val inStream = java.net.URL(url).openStream()
                val image = BitmapFactory.decodeStream(inStream)
                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(image)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    imageView.setImageResFromAttr(defaultImage)
                }
            }
        }
    }
}
