package com.example.githubandroidtrendingrepo.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubandroidtrendingrepo.R
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity
import com.example.githubandroidtrendingrepo.databinding.RepoListActivityBinding
import com.example.githubandroidtrendingrepo.ui.adapter.FilterListAdapter
import com.example.githubandroidtrendingrepo.ui.adapter.RepoListAdapter
import com.example.githubandroidtrendingrepo.ui.factory.ViewModelFactory
import com.example.githubandroidtrendingrepo.ui.listener.RecyclerItemClickListener
import com.example.githubandroidtrendingrepo.ui.listener.RecyclerLayoutClickListener
import com.example.githubandroidtrendingrepo.ui.listener.RecyclerViewPaginator
import com.example.githubandroidtrendingrepo.ui.viewmodel.RepoListViewModel
import com.example.githubandroidtrendingrepo.utils.AnimUtils
import com.example.githubandroidtrendingrepo.utils.AppUtils
import com.example.githubandroidtrendingrepo.utils.NavigatorUtils
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

/**
 *Created by Priyanka K on 9/20/2021
 */
class RepoListActivity: AppCompatActivity(), RecyclerLayoutClickListener {

@Inject
internal lateinit var viewModelFactory: ViewModelFactory
private lateinit var repoListActivityBinding : RepoListActivityBinding
    private lateinit var repoListViewModel: RepoListViewModel
    private lateinit var repoListAdapter: RepoListAdapter
    private lateinit var filterListAdapter: FilterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        initViewModel()
        initView()
    }

    private fun initView() {
        repoListActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_repo_list)
        setSupportActionBar(repoListActivityBinding.mainToolbar.toolbar)
        filterListAdapter = FilterListAdapter(Arrays.asList(*resources.getStringArray(R.array.list_filters)))
        repoListActivityBinding.filterList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        repoListActivityBinding.filterList.addOnItemTouchListener(
            RecyclerItemClickListener(applicationContext,
            object : RecyclerItemClickListener.OnRecyclerViewItemClickListener {
                override fun onItemClick(parentView: View, childView: View, position: Int) {
                    filterListAdapter.updateSelection(position)
                    repoListAdapter.filter.filter(filterListAdapter.getItem(position))
                }
            })
        )
        repoListActivityBinding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        repoListAdapter = RepoListAdapter(applicationContext, this)
        repoListActivityBinding.recyclerView.adapter = repoListAdapter
        repoListActivityBinding.recyclerView.addOnScrollListener(object : RecyclerViewPaginator(repoListActivityBinding.recyclerView) {
            override val isLastPage: Boolean
                get() = repoListViewModel.isLastPage()

            override fun loadMore() {
                repoListViewModel.fetchRepositories()
            }
        })

        /* This is to handle configuration changes:
         * during configuration change, when the activity
         * is recreated, we check if the viewModel
         * contains the list data. If so, there is no
         * need to call the api or load data from cache again */
        if (repoListViewModel.getRepositories().isEmpty()) {
            displayLoader()
            repoListViewModel.fetchRepositories()
        } else
            animateView(repoListViewModel.getRepositories())
    }

    private fun initViewModel() {
        repoListViewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoListViewModel::class.java)
        repoListViewModel.getRepositoryLiveData().observe(this, Observer { repositories ->
            if (repoListAdapter.itemCount == 0) {
                if(repositories.isNullOrEmpty()){
                    displayEmptyView()
                }else {
                    repositories?.let {
                        animateView(repositories)
                    }
                }

            } else {
                if(!repositories.isNullOrEmpty()) {
                    repositories?.let {
                        displayDataView(repositories)
                    }
                }
            }
        })
    }

    private fun displayLoader() {
        repoListActivityBinding.viewLoader.rootView.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        repoListActivityBinding.viewLoader.rootView.visibility = View.GONE
    }

    private fun animateView(repositories: List<GitRepoEntity>?) {
        hideLoader()
        displayDataView(repositories)
    }

    private fun displayDataView(repositories: List<GitRepoEntity>?) {
        repoListActivityBinding.viewEmpty.emptyContainer.visibility = View.GONE
        repoListAdapter.setItems(repositories)
    }

    private fun displayEmptyView() {
        hideLoader()
        repoListActivityBinding.viewEmpty.emptyContainer.visibility = View.VISIBLE
    }

    override fun redirectToDetailScreen(
        imageView: View,
        titleView: View,
        revealView: View,
        languageView: View,
        githubEntity: GitRepoEntity
    ) {
        NavigatorUtils.redirectToDetailScreen(this, githubEntity,
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, *AppUtils.getTransitionElements(
                applicationContext, imageView, titleView, revealView, languageView
            )))
    }

}