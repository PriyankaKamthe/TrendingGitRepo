package com.example.githubandroidtrendingrepo.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity
import com.example.githubandroidtrendingrepo.ui.activity.RepoDetailsActivity
import com.example.githubandroidtrendingrepo.utils.AppConstants.Companion.INTENT_POST

/**
 *Created by Priyanka K on 9/23/2021
 */
object NavigatorUtils {

    fun redirectToDetailScreen(activity: Activity,
                               githubEntity: GitRepoEntity,
                               options: ActivityOptionsCompat
    ) {

        val intent = Intent(activity, RepoDetailsActivity::class.java)
        intent.putExtra(INTENT_POST, githubEntity)
        ActivityCompat.startActivity(activity, intent, options.toBundle())
    }

    fun openBrowser(activity: Activity,
                    url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        activity.startActivity(i)
    }
}