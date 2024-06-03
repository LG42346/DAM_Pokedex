package dam.a42346.pokedex.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target // conflict with TARGET annotation
import com.google.android.material.card.MaterialCardView

object ViewBinding {
    @JvmStatic
    @BindingAdapter("android:src", "isToSetBackground")
    fun setRegionImage(imageView: AppCompatImageView, regionName: String, isToSetBackground: Boolean) {
        if (isToSetBackground) {
            val regionImageUri = "@drawable/bg_${regionName.lowercase()}"
            val regionDrawableId = imageView.context.resources.getIdentifier(regionImageUri,null, imageView.context.packageName)
            if(regionDrawableId != 0) imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, regionDrawableId))
        } else {
            val regionImageUri = "@drawable/pk_${regionName.lowercase()}"
            val regionDrawableId = imageView.context.resources.getIdentifier(regionImageUri,null, imageView.context.packageName)
            if(regionDrawableId != 0) imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, regionDrawableId))
        }
    }

    //fun bindLoadImagePalette(view: AppCompatImageView, url: String, paletteView: MaterialCardView) {
    @JvmStatic
    @BindingAdapter("paletteImage", "paletteCard")
    fun bindLoadImagePalette(view: AppCompatImageView, url: String, paletteCard: MaterialCardView) {
        Glide.with(view.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap>
            {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("TAG", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    p2: Target<Bitmap>?,
                    dataSource: DataSource,
                    p4: Boolean
                ): Boolean {
                    Log.d("TAG", "OnResourceReady")
                    val p: Palette = Palette.from(resource).generate()
                    val rgb = p.lightMutedSwatch?.rgb
                    if (rgb != null) {
                        paletteCard.setCardBackgroundColor(rgb)
                        //paletteView.setCardBackgroundColor(rgb)
                    }
                    return false
                }
            })
            .into(view)
    }
}