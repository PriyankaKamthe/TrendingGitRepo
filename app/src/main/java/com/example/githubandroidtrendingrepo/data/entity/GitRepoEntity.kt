package com.example.githubandroidtrendingrepo.data.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.githubandroidtrendingrepo.utils.TimestampConverter
import com.google.gson.annotations.SerializedName

/**
 *Created by Priyanka K on 9/20/2021
 */
@Entity
class GitRepoEntity(@PrimaryKey
                    val id: Long,

                    var page: Long,

                    var totalPages: Long,

                    var name: String,

                    @SerializedName("full_name")
                    var fullName: String,

                    @Embedded
                    var owner: Owner,

                    @SerializedName("html_url")
                    var htmlUrl: String,

                    var description: String,

                    @SerializedName("contributors_url")
                    var contributorsUrl: String,

                    @TypeConverters(TimestampConverter::class)
                    @SerializedName("created_at")
                    var createdAt: String,

                    @SerializedName("stargazers_count")
                    var starsCount: Long,

                    var watchers: Long,

                    var forks: Long,

                    var language: String?): Parcelable {


    fun isLastPage(): Boolean {
        return page >= totalPages
    }

    constructor(source: Parcel) : this(
        source.readLong(),
        source.readLong(),
        source.readLong(),
        source.readString()!!,
        source.readString()!!,
        source.readParcelable<Owner>(Owner::class.java.classLoader)!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readLong(),
        source.readLong(),
        source.readLong(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeLong(page)
        writeLong(totalPages)
        writeString(name)
        writeString(fullName)
        writeParcelable(owner, 0)
        writeString(htmlUrl)
        writeString(description)
        writeString(contributorsUrl)
        writeString(createdAt)
        writeLong(starsCount)
        writeLong(watchers)
        writeLong(forks)
        writeString(language)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GitRepoEntity> = object : Parcelable.Creator<GitRepoEntity> {
            override fun createFromParcel(source: Parcel): GitRepoEntity = GitRepoEntity(source)
            override fun newArray(size: Int): Array<GitRepoEntity?> = arrayOfNulls(size)
        }
    }
}