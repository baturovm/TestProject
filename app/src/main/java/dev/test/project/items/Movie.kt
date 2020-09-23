package dev.test.project.items

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.test.project.utils.StringRealmListParceler
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith

@Parcelize
@RealmClass
open class Movie(
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("localized_name")
    var titleRU: String? = null,
    @SerializedName("name")
    var titleEN: String? = null,
    @SerializedName("year")
    var year: Int = 0,
    @SerializedName("rating")
    var rating: Double = 0.0,
    @SerializedName("image_url")
    var imageURL: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("genres")
    var genres: @WriteWith<StringRealmListParceler>  RealmList<String>? = null,
    var favorited: Boolean = false
) : RealmModel, Parcelable