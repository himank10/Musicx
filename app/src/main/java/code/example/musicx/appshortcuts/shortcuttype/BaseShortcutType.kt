
package code.example.musicx.appshortcuts.shortcuttype

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.os.Build
import android.os.Bundle
import code.example.musicx.appshortcuts.AppShortcutLauncherActivity

@TargetApi(Build.VERSION_CODES.N_MR1)
abstract class BaseShortcutType(internal var context: Context) {

    internal abstract val shortcutInfo: ShortcutInfo

    /**
     * Creates an Intent that will launch MainActivtiy and immediately play {@param songs} in either shuffle or normal mode
     *
     * @param shortcutType Describes the type of shortcut to create (ShuffleAll, TopTracks, custom playlist, etc.)
     * @return
     */
    internal fun getPlaySongsIntent(shortcutType: Long): Intent {
        val intent = Intent(context, AppShortcutLauncherActivity ::class.java)
        intent.action = Intent.ACTION_VIEW
        val b = Bundle()
        b.putLong(AppShortcutLauncherActivity.KEY_SHORTCUT_TYPE, shortcutType)
        intent.putExtras(b)
        return intent
    }

    companion object {
        internal const val ID_PREFIX = "io.github.muntashirakon.Music.appshortcuts.id."
        val id: String
            get() = ID_PREFIX + "invalid"
    }
}
