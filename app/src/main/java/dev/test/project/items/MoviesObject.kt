package dev.test.project.items

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MoviesObject(
    @SerializedName("films")
    var movies: List<Movie>,
    var genres: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Movie)!!,
        parcel.createStringArrayList()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(movies)
        parcel.writeStringList(genres)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoviesObject> {
        override fun createFromParcel(parcel: Parcel): MoviesObject {
            return MoviesObject(parcel)
        }

        override fun newArray(size: Int): Array<MoviesObject?> {
            return arrayOfNulls(size)
        }
    }
}