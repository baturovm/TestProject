package dev.test.project.items

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("localized_name")
    val titleRU: String?,
    @SerializedName("name")
    val titleEN: String?,
    @SerializedName("year")
    val year: Int,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("image_url")
    val imageURL: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("genres")
    val genres: ArrayList<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(titleRU)
        parcel.writeString(titleEN)
        parcel.writeInt(year)
        parcel.writeString(rating)
        parcel.writeString(imageURL)
        parcel.writeString(description)
        parcel.writeStringList(genres)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}