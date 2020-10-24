package com.lokalhy.tyro.epoxyviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.google.android.material.textfield.TextInputEditText
import com.lokalhy.tyro.R
import com.lokalhy.tyro.base.bindView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SearchBoxView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?= null,
        defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.searchbox_view, this, true)
    }


    private val etSearchBar by bindView<TextInputEditText>(R.id.et_search_bar)

    init {
        etSearchBar.requestFocus()
        etSearchBar.setOnClickListener {
            onClick?.invoke(etSearchBar)
        }
    }

    var onClick: ((TextInputEditText) -> Unit)? = null
        @CallbackProp set
}
