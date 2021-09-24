package com.example.githubandroidtrendingrepo.data.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 *Created by Priyanka K on 9/20/2021
 */
@Entity
class Owner(var login: String,

            @SerializedName("avatar_url")
            var avatarUrl: String) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Owner> = object : Parcelable.Creator<Owner> {
            override fun createFromParcel(source: Parcel): Owner = Owner(source)
            override fun newArray(size: Int): Array<Owner?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(login)
        writeString(avatarUrl)
    }

}