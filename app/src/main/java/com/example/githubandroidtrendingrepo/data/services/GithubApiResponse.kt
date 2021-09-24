package com.example.githubandroidtrendingrepo.data.services

import android.os.Parcel
import android.os.Parcelable
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity
import com.google.gson.annotations.SerializedName

/**
 *Created by Priyanka K on 9/21/2021
 */
class GithubApiResponse(@SerializedName("total_count")
                        var totalCount: Long,

                        var items: List<GitRepoEntity>) : Parcelable {

    constructor(source: Parcel) : this(
        source.readLong(),
        source.createTypedArrayList(GitRepoEntity.CREATOR)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(totalCount)
        writeTypedList(items)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GithubApiResponse> = object : Parcelable.Creator<GithubApiResponse> {
            override fun createFromParcel(source: Parcel): GithubApiResponse = GithubApiResponse(source)
            override fun newArray(size: Int): Array<GithubApiResponse?> = arrayOfNulls(size)
        }
    }
}