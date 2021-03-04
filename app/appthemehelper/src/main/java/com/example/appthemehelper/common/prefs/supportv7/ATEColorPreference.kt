
package com.example.appthemehelper.common.prefs.supportv7

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.example.appthemehelper.R
import com.example.appthemehelper.common.prefs.BorderCircleView
import com.example.appthemehelper.util.ATHUtil

class ATEColorPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Preference(context, attrs, defStyleAttr) {

    private var mView: View? = null
    private var color: Int = 0
    private var border: Int = 0

    init {
        widgetLayoutResource = R.layout.ate_preference_color
        isPersistent = false

        icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            ATHUtil.resolveColor(
                context,
                android.R.attr.colorControlNormal
            ), BlendModeCompat.SRC_IN
        )
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        mView = holder.itemView
        invalidateColor()
    }

    fun setColor(color: Int, border: Int) {
        this.color = color
        this.border = border
        invalidateColor()
    }

    private fun invalidateColor() {
        if (mView != null) {
            val circle = mView!!.findViewById<View>(R.id.circle) as? BorderCircleView
            if (this.color != 0) {
                if (circle != null) {
                    circle.visibility = View.VISIBLE
                }
                if (circle != null) {
                    circle.setBackgroundColor(color)
                }
                if (circle != null) {
                    circle.setBorderColor(border)
                }
            } else {
                if (circle != null) {
                    circle.visibility = View.GONE
                }
            }
        }
    }
}