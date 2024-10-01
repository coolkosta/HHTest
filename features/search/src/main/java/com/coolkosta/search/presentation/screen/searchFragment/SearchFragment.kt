package com.coolkosta.search.presentation.screen.searchFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.coolkosta.search.R
import com.coolkosta.search.databinding.FragmentSearchBinding
import com.coolkosta.search.di.SearchComponentProvider
import com.coolkosta.search.domain.model.OfferEntity
import com.coolkosta.core.domain.model.VacanciesCount
import com.coolkosta.core.domain.model.VacanciesItems
import com.coolkosta.search.presentation.adapter.SearchAdapterDelegate
import com.coolkosta.search.presentation.screen.vacancyDetailsFragment.VacancyDetailsFragment
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val adapter = SearchAdapterDelegate()
    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel: SearchViewModel by viewModels {
        (requireActivity().application as SearchComponentProvider)
            .getSearchComponent().searchViewModelFactory()
    }
    private lateinit var adapterOffers: ListDelegationAdapter<List<OfferEntity>>
    private lateinit var adapterVacancies: ListDelegationAdapter<List<VacanciesItems>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (binding.searchBtn.isClickable) {
            binding.searchBtn.setOnClickListener {
                viewModel.sendEvent(SearchEvent.VacancyListOpened(false))
            }
        }

        adapterOffers = ListDelegationAdapter(adapter.offerAdapterDelegate { offer ->
            val url = offer.link
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        })

        adapterVacancies = ListDelegationAdapter(
            adapter.vacancyAdapterDelegate(
                onLikeIconClick = { vacancy ->
                    viewModel.sendEvent(SearchEvent.VacancyFavoriteItemsChanged(vacancy))
                },
                onClick = {
                    requireActivity().supportFragmentManager.beginTransaction().replace(
                        com.coolkosta.core.R.id.fragment_container,
                        VacancyDetailsFragment::class.java,
                        null
                    )
                        .addToBackStack(null)
                        .commit()
                }
            ),
            adapter.showMoreVacanciesAdapterDelegate {
                viewModel.sendEvent(SearchEvent.VacancyListOpened(true))
            }
        )

        with(binding) {
            recommendationRv.adapter = adapterOffers
            vacanciesRv.adapter = adapterVacancies
        }
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is SearchState.Loading -> {
                                binding.recommendationRv.visibility = View.GONE
                                binding.vacanciesRv.visibility = View.GONE
                            }

                            is SearchState.Error -> {
                                binding.recommendationRv.visibility = View.GONE
                                binding.vacanciesRv.visibility = View.GONE
                            }

                            is SearchState.Success -> {
                                binding.recommendationRv.visibility = View.VISIBLE
                                binding.vacanciesRv.visibility = View.VISIBLE
                                adapterOffers.apply {
                                    items = state.offers
                                    notifyDataSetChanged()
                                }
                                updateVacancyList(state)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateVacancyList(state: SearchState.Success) {
        if (state.isListFull) {
            with(binding) {
                searchBtn.isClickable = true
                searchBtn.setImageResource(com.coolkosta.core.R.drawable.ic_back)
                vacanciesCounterContainer.visibility = View.VISIBLE
                recommendationRv.visibility = View.GONE
                vacanciesForYouTv.visibility = View.GONE
                vacanciesCounterTv.text = resources.getQuantityString(
                    com.coolkosta.core.R.plurals.counter_vacancies,
                    state.vacanciesCount.count,
                    state.vacanciesCount.count
                )
            }
            adapterVacancies.apply {
                items = state.vacancies
                notifyDataSetChanged()
            }
        } else {
            binding.vacanciesCounterContainer.visibility = View.GONE
            with(binding) {
                searchBtn.setImageResource(com.coolkosta.core.R.drawable.ic_search)
                searchBtn.isClickable = false
                recommendationRv.visibility = View.VISIBLE
                vacanciesForYouTv.visibility = View.VISIBLE
            }
            adapterVacancies.apply {
                val previewCounter = VacanciesCount(state.vacanciesCount.count - 3)
                items = state.vacancies.slice(0..2) + previewCounter
                notifyDataSetChanged()
            }
        }
    }
}