
package com.example.musicx.interfaces

import com.afollestad.materialcab.MaterialCab

interface ICabHolder {

    fun openCab(menuRes: Int, callback: MaterialCab.Callback): MaterialCab
}
