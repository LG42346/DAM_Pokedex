package dam.a42346.pokedex.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object ViewBinding {
    @JvmStatic
    @BindingAdapter("android:src", "isToSetBackground")
    fun setRegionImage(imageView: AppCompatImageView, regionName: String, isToSetBackground: Boolean) {
        if (isToSetBackground) {
            val regionImageUri = "@drawable/bg_${regionName.lowercase()}"
            val regionDrawableId = imageView.context.resources.getIdentifier(regionImageUri,null, imageView.context.packageName)
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, regionDrawableId))
        } else {
            val regionImageUri = "@drawable/pk_${regionName.lowercase()}"
            val regionDrawableId = imageView.context.resources.getIdentifier(regionImageUri,null, imageView.context.packageName)
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, regionDrawableId))
        }
    }
}