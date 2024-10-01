package com.coolkosta.search.presentation.adapter

import android.view.View
import com.coolkosta.core.databinding.ItemVacancyCardBinding
import com.coolkosta.search.R
import com.coolkosta.search.databinding.ButtonShowMoreBinding
import com.coolkosta.search.databinding.ItemRecommendationBinding
import com.coolkosta.search.domain.model.OfferEntity
import com.coolkosta.search.domain.model.VacanciesCount
import com.coolkosta.search.domain.model.VacanciesItems
import com.coolkosta.search.domain.model.VacancyEntity
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class SearchAdapterDelegate {

    fun offerAdapterDelegate(
        itemClickListener: (OfferEntity) -> Unit
    ) = adapterDelegateViewBinding<OfferEntity, OfferEntity, ItemRecommendationBinding>(
        { layoutInflater, root -> ItemRecommendationBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener { itemClickListener.invoke(item) }

        bind {
            with(binding) {
                val offer = item

                when (offer.id) {
                    "near_vacancies" -> {
                        recommendationIv.setImageDrawable(getDrawable(R.drawable.near_vacancies))
                    }

                    "level_up_resume" -> {
                        recommendationIv.setImageDrawable(getDrawable(R.drawable.level_up_resume))
                    }

                    "temporary_job" -> {
                        recommendationIv.setImageDrawable(getDrawable(R.drawable.temporary_job))
                    }

                    else -> {
                        recommendationIv.visibility = View.GONE
                    }
                }
                recommendationTv.text = offer.title

                if (offer.buttonText.isNullOrEmpty()) {
                    buttonTv.visibility = View.GONE
                } else {
                    buttonTv.text = offer.buttonText
                    buttonTv.visibility = View.VISIBLE
                }
            }
        }
    }

    fun vacancyAdapterDelegate(
        onLikeIconClick: (VacancyEntity) -> Unit,
        onClick: () -> Unit,
    ) =
        adapterDelegateViewBinding<VacancyEntity, VacanciesItems, ItemVacancyCardBinding>(
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
                            onLikeIconClick.invoke(vacancy)
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

    fun showMoreVacanciesAdapterDelegate(
        onClick: () -> Unit
    ) = adapterDelegateViewBinding<VacanciesCount, VacanciesItems, ButtonShowMoreBinding>(
        { layoutInflater, root ->
            ButtonShowMoreBinding.inflate(
                layoutInflater, root, false
            )
        }
    ) {

        bind {
            binding.showMoreBtn.setOnClickListener { onClick.invoke() }
            with(binding) {
                val title = "Еще " + context.resources.getQuantityString(
                    com.coolkosta.core.R.plurals.counter_vacancies,
                    item.count,
                    item.count
                )
                showMoreBtn.text = title
            }
        }
    }
}