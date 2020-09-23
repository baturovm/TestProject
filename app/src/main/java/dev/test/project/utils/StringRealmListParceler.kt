package dev.test.project.utils

import android.os.Parcel
import io.realm.RealmList
import kotlinx.android.parcel.Parceler

object StringRealmListParceler: Parceler<RealmList<String>?> {
    override fun create(parcel: Parcel): RealmList<String>? = parcel.readStringRealmList()

    override fun RealmList<String>?.write(parcel: Parcel, flags: Int) {
        parcel.writeStringRealmList(this)
    }
}

fun Parcel.readStringRealmList(): RealmList<String>? = when {
    readInt() > 0 -> RealmList<String>().also { list ->
        repeat(readInt()) {
            list.add(readString())
        }
    }
    else -> null
}

fun Parcel.writeStringRealmList(realmList: RealmList<String>?) {
    writeInt(
        when (realmList) {
            null -> 0
            else -> 1
        }
    )
    if (realmList != null) {
        writeInt(realmList.size)
        for (t in realmList) {
            writeString(t)
        }
    }
}