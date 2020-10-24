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

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PodCastView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?= null,
        defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.podcast_view, this, true)
    }


    private val tvTitle by bindView<TextView>(R.id.title)
    private val tvPubDate by bindView<TextView>(R.id.pubDate)

    private val share by bindView<ImageView>(R.id.share)

    init {
        share.setOnClickListener {
            onShareClick?.invoke()
        }
    }

    var onShareClick: (() -> Unit)? = null
        @CallbackProp set

    @ModelProp
    lateinit var title: String

    @ModelProp
    lateinit var pubDate: String


    @AfterPropsSet
    fun setData() {
        tvTitle.text = title
        tvPubDate.text = pubDate
    }

}