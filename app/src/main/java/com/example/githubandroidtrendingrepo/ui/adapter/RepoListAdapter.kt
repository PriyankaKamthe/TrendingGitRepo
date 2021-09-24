package com.example.githubandroidtrendingrepo.ui.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.githubandroidtrendingrepo.R
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity
import com.example.githubandroidtrendingrepo.databinding.RepoListItemBinding
import com.example.githubandroidtrendingrepo.ui.listener.RecyclerLayoutClickListener
import com.example.githubandroidtrendingrepo.utils.AppUtils
import com.squareup.picasso.Picasso
import java.util.ArrayList

/**
 *Created by Priyanka K on 9/20/2021
 */
class RepoListAdapter(private val context: Context, private val listener: RecyclerLayoutClickListener) : RecyclerView.Adapter<RepoListAdapter.CustomViewHolder>(),
    Filterable  {

    private var items: MutableList<GitRepoEntity> = mutableListOf()
    private val filteredItems: MutableList<GitRepoEntity> = mutableListOf()

    private var lastFilteredLanguage = "All"

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val itemBinding = RepoListItemBinding.inflate(layoutInflater, parent, false)
        val customItemViewHolder = CustomViewHolder(itemBinding)

        itemBinding.cardView.setOnClickListener { v -> customItemViewHolder.onLayoutButtonClick() }
//        itemBinding.btnShare.setOnClickListener { view -> customItemViewHolder.onShareButtonClick() }

        return customItemViewHolder
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
    fun setItems(items: List<GitRepoEntity>?) {
        items?.let {
            filteredItems.addAll(items)
            filter.filter(lastFilteredLanguage)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun getItem(position: Int): GitRepoEntity {
        return items[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val language = charSequence.toString()
                lastFilteredLanguage = language

                if (language.equals("All", ignoreCase = true)) {
                    items = filteredItems

                } else {
                    val list = ArrayList<GitRepoEntity>()
                    for (gitRepoEntity in filteredItems) {

                        if (language.equals(gitRepoEntity.language, ignoreCase = true)) {
                            list.add(gitRepoEntity)
                        }
                    }
                    items = list
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = items
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                items = filterResults.values as MutableList<GitRepoEntity>
                notifyDataSetChanged()
            }
        }
    }

    inner class CustomViewHolder(private val binding: RepoListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(gitRepoEntity: GitRepoEntity) {
            Picasso.get().load(gitRepoEntity.owner.avatarUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.itemProfileImg)

            binding.itemTitle.text = gitRepoEntity.fullName
            binding.itemTime.text = String.format(context.getString(R.string.item_date),
                AppUtils.getDate(gitRepoEntity.createdAt),
                AppUtils.getTime(gitRepoEntity.createdAt))

            binding.itemDesc.text = gitRepoEntity.description


            gitRepoEntity.language?.let {
                binding.itemImgLanguage.visibility = View.VISIBLE
                binding.itemLikes.visibility = View.VISIBLE
                binding.itemLikes.text = gitRepoEntity.language

                val drawable = context.resources.getDrawable(R.drawable.ic_circle) as GradientDrawable
                drawable.setColor(AppUtils.getColorByLanguage(context, gitRepoEntity.language))
                binding.itemImgLanguage.setImageDrawable(drawable)
            } ?: run {
                binding.itemImgLanguage.visibility = View.GONE
                binding.itemLikes.visibility = View.GONE
            }
        }

        internal fun onLayoutButtonClick() {
            listener.redirectToDetailScreen(binding.itemProfileImg, binding.itemTitle, binding.itemImgLanguage, binding.itemLikes, getItem(layoutPosition))
        }

    }
}