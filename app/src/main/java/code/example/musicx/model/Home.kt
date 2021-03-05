

package code.example.musicx.model

import androidx.annotation.StringRes
import code.example.musicx.HomeSection


data class Home(
    val arrayList: List<Any>,
    @HomeSection
    val homeSection: Int,
    @StringRes
    val titleRes: Int
)