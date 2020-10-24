package com.lokalhy.tyro.epoxyviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.lokalhy.tyro.R
import com.lokalhy.tyro.base.bindView
import com.squareup.picasso.Picasso

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PodCastAuthorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_holder_podcast_authors, this, true)

        setOnClickListener {
            onClick?.invoke()
        }
    }



    var onClick: (() -> Unit)? = null
        @CallbackProp set



    private val tvAuthor by bindView<TextView>(R.id.authorName)
    private val ivAuthor by bindView<ImageView>(R.id.authorImage)


    @ModelProp
    lateinit var authorName: String

    @ModelProp
    lateinit var authorImage: String


    @AfterPropsSet
    fun setData() {
        tvAuthor.text = authorName
        Picasso.get().load(authorImage).into(ivAuthor)
    }
}
