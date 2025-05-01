package id.herdroid.newsapp.presentation.view.article

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.newsapp.databinding.FragmentArticleBinding
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import kotlinx.coroutines.Job
import androidx.paging.cachedIn
import id.herdroid.newsapp.R
import id.herdroid.newsapp.utils.isNetworkAvailable
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleViewModel by viewModels()
    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var adapter: ArticlePagingAdapter
    private var currentJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ArticlePagingAdapter {
            val action = ArticleFragmentDirections.actionArticleFragmentToDetailActivity(it)
            findNavController().navigate(action)
        }

        binding.recyclerViewArticles.adapter = adapter

        adapter.addLoadStateListener { loadStates ->
            val isListEmpty = loadStates.refresh is androidx.paging.LoadState.NotLoading &&
                    adapter.itemCount == 0

            binding.tvEmptyState.visibility = if (isListEmpty) View.VISIBLE else View.GONE
            binding.recyclerViewArticles.visibility = if (isListEmpty) View.GONE else View.VISIBLE
        }

        showShimmer(true)
        viewModel.offlineArticles.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.submitData(lifecycle, PagingData.from(it))
                showShimmer(false)
            }
        }

        binding.recyclerViewArticles.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        )

        fetchArticles(args.sourceId)

        if (!requireContext().isNetworkAvailable()) {
            Toast.makeText(requireContext(), "Kamu sedang offline. Menampilkan artikel cache", Toast.LENGTH_LONG).show()

            binding.tvNetworkStatus.apply {
                visibility = View.VISIBLE
                text = "Kamu sedang offline"
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun showShimmer(isLoading: Boolean) {
        val shimmerView = binding.shimmerView.root.findViewById<View>(R.id.shimmerLayout)

        if (shimmerView is com.facebook.shimmer.ShimmerFrameLayout) {
            if (isLoading) {
                shimmerView.startShimmer()
                shimmerView.visibility = View.VISIBLE
                binding.recyclerViewArticles.visibility = View.GONE
            } else {
                shimmerView.stopShimmer()
                shimmerView.visibility = View.GONE
                binding.recyclerViewArticles.visibility = View.VISIBLE
            }
        }

        binding.tvEmptyState.visibility = View.GONE
    }



    private fun fetchArticles(sourceId: String) {
        currentJob?.cancel()
        currentJob = lifecycleScope.launch {
            try {
                showShimmer(true)
                viewModel.getArticlesPager(sourceId).flow
                    .cachedIn(viewLifecycleOwner.lifecycleScope)
                    .collectLatest {
                        showShimmer(false)
                        adapter.submitData(it)
                    }


            } catch (e: Exception) {
                viewModel.loadOfflineArticles()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
