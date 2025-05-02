package id.herdroid.newsapp.presentation.view.sourcebycategory

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.newsapp.R
import id.herdroid.newsapp.data.model.CategoryDataDummy
import id.herdroid.newsapp.data.model.Source
import id.herdroid.newsapp.databinding.FragmentCategorySourceBinding
import id.herdroid.newsapp.utils.isNetworkAvailable

@AndroidEntryPoint
class CategorySourceFragment : Fragment() {

    private var _binding: FragmentCategorySourceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategorySourceViewModel by viewModels()
    private lateinit var adapter: SourceAdapter

    private val categories = CategoryDataDummy.getCategories()
    private var isOnline: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCategorySourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ServiceCast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isOnline = requireContext().isNetworkAvailable()

        binding.searchView.isFocusable = false
        binding.searchView.setIconifiedByDefault(false)
        binding.searchView.isIconified = false

        binding.searchView.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        binding.root.postDelayed({
            binding.searchView.clearFocus()
            val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }, 300)

        setupTabs()
        setupRecyclerView()
        setupSearchView()
        showShimmer(true)

        viewModel.sources.observe(viewLifecycleOwner) {
            showShimmer(false)
            adapter.submitList(it)
            handleEmptyState(it)
            if (it.isNotEmpty()) {
                binding.recyclerViewSources.startAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                )
            }
        }

        binding.tabLayout.getTabAt(0)?.select()
        val firstCategory = categories.first().name
        viewModel.loadSources(firstCategory, isOnline)
    }

    private fun handleEmptyState(list: List<Source>) {
        binding.tvEmptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerViewSources.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun showShimmer(isLoading: Boolean) {
        binding.shimmerView.shimmerLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.recyclerViewSources.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvEmptyState.visibility = View.GONE
    }

    private fun setupTabs() {
        categories.forEach { category ->
            val tab = binding.tabLayout.newTab().setText(category.displayName)
            binding.tabLayout.addTab(tab)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val category = categories[tab.position].name
                binding.searchView.setQuery("", false)
                showShimmer(true)
                viewModel.loadSources(category, isOnline)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    @SuppressLint("ServiceCast")
    private fun setupRecyclerView() {
        adapter = SourceAdapter {
            val action = CategorySourceFragmentDirections.actionCategorySourceFragmentToArticleFragment(it.id)
            findNavController().navigate(action)
            val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        binding.recyclerViewSources.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSources.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterSources(newText.orEmpty())
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ServiceCast")
    override fun onResume() {
        super.onResume()
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    @SuppressLint("ServiceCast")
    override fun onPause() {
        super.onPause()
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

