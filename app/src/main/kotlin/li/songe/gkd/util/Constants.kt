package li.songe.gkd.util

import android.webkit.URLUtil
import io.ktor.http.Url
import io.ktor.http.fullPath
import li.songe.gkd.BuildConfig

const val VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION"

const val FILE_UPLOAD_URL = "https://u.gkd.li/"
const val IMPORT_BASE_URL = "https://i.gkd.li/import/"

const val UPDATE_URL = "https://registry.npmmirror.com/@gkd-kit/app/latest/files/index.json"

const val SERVER_SCRIPT_URL =
    "https://registry-direct.npmmirror.com/@gkd-kit/config/latest/files/dist/server.js"

const val REPOSITORY_URL = "https://github.com/gkd-kit/gkd"

const val HOME_PAGE_URL = "https://gkd.li/"

@Suppress("SENSELESS_COMPARISON")
val GIT_COMMIT_URL = if (BuildConfig.GIT_COMMIT_ID != null) {
    "https://github.com/gkd-kit/gkd/commit/${BuildConfig.GIT_COMMIT_ID}"
} else {
    null
}

private val safeRemoteBaseUrls = arrayOf(
    "https://registry.npmmirror.com/@gkd-kit/",

    "https://github.com/gkd-kit/",
    "https://raw.githubusercontent.com/gkd-kit/",
)

fun isSafeUrl(url: String): Boolean {
    if (!URLUtil.isHttpsUrl(url)) return false
    if (safeRemoteBaseUrls.any { u -> url.startsWith(u) }) {
        return true
    }
    val u = try {
        Url(url)
    } catch (e: Exception) {
        return false
    }
    if (u.host == "gkd.li" || u.host.endsWith(".gkd.li")) {
        return true
    } else if (u.host.endsWith(".jsdelivr.net") && u.fullPath.startsWith("/npm/@gkd-kit/")) {
        return true
    } else if ((u.host == "unpkg.com" || u.host.endsWith(".unpkg.com")) && u.fullPath.startsWith("/@gkd-kit/")) {
        return true
    }
    return false
}
