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
import com.google.android.material.textfield.TextInputEditText
import com.lokalhy.tyro.R
import com.lokalhy.tyro.base.bindView
import com.squareup.picasso.Picasso

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PodCastDetailTopView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?= null,
        defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.podcast_detail_top_view, this, true)
    }

    private val tvTitle by bindView<TextView>(R.id.title)

    private val tvAuthor by bindView<TextView>(R.id.authorName)
    private val ivAuthor by bindView<ImageView>(R.id.authorImage)
    private val tvDesc by bindView<TextView>(R.id.description)



    @ModelProp
    lateinit var authorName: String

    @ModelProp
    lateinit var authorImage: String

    @ModelProp
    lateinit var description: String

    @ModelProp
    lateinit var title: String



    @AfterPropsSet
    fun setData() {
        tvAuthor.text = authorName
        tvTitle.text = title
        tvDesc.text = description
        Picasso.get().load(authorImage).fit().into(ivAuthor)
    }
}
