package com.elan.media.server.android.data.model

import android.os.Parcelable
import com.elan.media.server.android.data.constants.Category
import kotlinx.parcelize.Parcelize

@Parcelize
class FileDto : Parcelable {
    var id: String? = null
    var fileName: String? = null
    var thumbnail: String? = null
    var size: String? = null
    var contentType: String? = null
    var category: Category? = null;
    var isMedia: Boolean? = null
}
