package com.reddit.client.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.reddit.client.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var pagingAdapter: EntryAdapter

    val viewModel: MainViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pagingAdapter = EntryAdapter()
        list_view.layoutManager = LinearLayoutManager(requireActivity())
        list_view.adapter = pagingAdapter
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                refresh_view.isRefreshing = loadStates.refresh == LoadState.Loading
            }
        }
        lifecycleScope.launch {
            viewModel.getTop().collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
        refresh_view.setOnRefreshListener {
            pagingAdapter.refresh()
        }
    }

}