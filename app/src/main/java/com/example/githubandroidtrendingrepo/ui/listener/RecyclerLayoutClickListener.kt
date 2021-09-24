package com.example.githubandroidtrendingrepo.ui.listener

import android.view.View
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity

/**
 *Created by Priyanka K on 9/20/2021
 */
interface RecyclerLayoutClickListener {
    fun redirectToDetailScreen(imageView: View,
                               titleView: View,
                               revealView: View,
                               languageView: View,
                               githubEntity: GitRepoEntity)
}