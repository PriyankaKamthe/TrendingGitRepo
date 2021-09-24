package com.example.githubandroidtrendingrepo.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.githubandroidtrendingrepo.R
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity
import com.example.githubandroidtrendingrepo.databinding.RepoDetailActivityBinding
import com.example.githubandroidtrendingrepo.utils.AppConstants.Companion.INTENT_POST
import com.example.githubandroidtrendingrepo.utils.AppUtils
import com.example.githubandroidtrendingrepo.utils.NavigatorUtils
import com.squareup.picasso.Picasso

/**
 *Created by Priyanka K on 9/23/2021
 */
class RepoDetailsActivity: AppCompatActivity() {
    private lateinit var githubEntity: GitRepoEntity
    private lateinit var binding: RepoDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseView()
    }
    private fun initialiseView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail)
        githubEntity = intent.getParcelableExtra(INTENT_POST)!!

        Picasso.get().load(githubEntity.owner.avatarUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.itemProfileImg)

        binding.itemTitle.text = githubEntity.fullName
        binding.itemStars.text = githubEntity.starsCount.toString()
        binding.itemWatchers.text = githubEntity.watchers.toString()
        binding.itemForks.text = githubEntity.forks.toString()

        githubEntity.language?.let {
            binding.itemImgLanguage.visibility = View.VISIBLE
            binding.itemLanguage.visibility = View.VISIBLE
            binding.itemLanguage.text = githubEntity.language
            updateColorTheme()
        }
        binding.btnVisit.setOnClickListener { NavigatorUtils.openBrowser(this, githubEntity.htmlUrl) }
    }

    private fun updateColorTheme() {
        val bgColor = AppUtils.getColorByLanguage(applicationContext, githubEntity.language)

        binding.appBarLayout.setBackgroundColor(bgColor)
        binding.mainToolbar.toolbar.setBackgroundColor(bgColor)
        binding.itemImgLanguage.setImageDrawable(AppUtils.updateGradientDrawableColor(applicationContext, bgColor))
        binding.btnVisit.setBackgroundDrawable(AppUtils.updateStateListDrawableColor(resources.getDrawable(R.drawable.btn_visit), bgColor))
        AppUtils.updateStatusBarColor(this, bgColor)
    }


}