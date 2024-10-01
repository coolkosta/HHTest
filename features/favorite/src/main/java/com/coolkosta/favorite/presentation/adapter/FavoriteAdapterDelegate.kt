package com.coolkosta.favorite.presentation.adapter

import android.view.View
import com.coolkosta.core.databinding.ItemVacancyCardBinding
import com.coolkosta.core.domain.model.VacancyEntity
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class FavoriteAdapterDelegate {
    fun vacancyAdapterDelegate(
        onLikeIconClick: (VacancyEntity) -> Unit,
        onClick: () -> Unit,
    ) = adapterDelegateViewBinding<VacancyEntity, VacancyEntity, ItemVacancyCardBinding>(
        { layoutInflater, root -> ItemVacancyCardBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener {
            onClick.invoke()
        }
        bind {
            with(binding) {
                val vacancy = item
                if (vacancy.lookingNumber == 0) {
                    watchersTv.visibility = View.GONE
                } else {
                    watchersTv.text = context.resources.getQuantityString(
                        com.coolkosta.core.R.plurals.counter_watchers,
                        vacancy.lookingNumber, vacancy.lookingNumber
                    )
                }
                favoriteIv.apply {
                    if (vacancy.isFavorite) {
                        setImageResource(com.coolkosta.core.R.drawable.ic_favorite_filled)
                    } else {
                        setImageResource(com.coolkosta.core.R.drawable.ic_favorite)
                    }
                    setOnClickListener {
                        val updatedItem = vacancy.copy(isFavorite = !vacancy.isFavorite)
                        onLikeIconClick.invoke(updatedItem)
                    }
                }
                positionTv.text = vacancy.title
                cityTv.text = vacancy.city
                companyTv.text = vacancy.company
                experienceTv.text = vacancy.experience
                sharingTv.text = vacancy.publishedDate
            }
        }
    }
}