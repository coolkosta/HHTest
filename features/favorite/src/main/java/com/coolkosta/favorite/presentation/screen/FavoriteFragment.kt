package com.coolkosta.favorite.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.coolkosta.core.domain.model.VacancyEntity
import com.coolkosta.favorite.R
import com.coolkosta.favorite.databinding.FragmentFavoriteBinding
import com.coolkosta.favorite.di.FavoriteComponentProvider
import com.coolkosta.favorite.presentation.adapter.FavoriteAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val adapter = FavoriteAdapterDelegate()
    private val binding: FragmentFavoriteBinding by viewBinding()
    private val viewModel: FavoriteViewModel by viewModels {
        (requireActivity().application as FavoriteComponentProvider)
            .getFavoriteComponent()
            .provideFavoriteViewModelFactory()
    }
    private lateinit var adapterVacancies: ListDelegationAdapter<List<VacancyEntity>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterVacancies = ListDelegationAdapter(adapter.vacancyAdapterDelegate(
            onLikeIconClick = {
                viewModel.sendEvent(FavoriteEvent.VacancyFavoriteItemsChanged(it))
            },
            onClick = {

            }
        ))

        with(binding) {
            recyclerView.adapter = adapterVacancies
        }
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is FavoriteState.Loading -> {
                                binding.recyclerView.visibility = View.GONE
                            }

                            is FavoriteState.Error -> {
                                binding.recyclerView.visibility = View.GONE
                            }

                            is FavoriteState.Success -> {
                                with(binding) {
                                    recyclerView.visibility = View.VISIBLE
                                    vacancyCounterTv.text = resources.getQuantityString(
                                        com.coolkosta.core.R.plurals.counter_vacancies,
                                        state.vacancies.size,
                                        state.vacancies.size
                                    )
                                }
                                adapterVacancies.apply {
                                    items = state.vacancies
                                    notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}