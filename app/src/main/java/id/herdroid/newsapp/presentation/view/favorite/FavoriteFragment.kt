package id.herdroid.newsapp.presentation.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.newsapp.databinding.FragmentFavoriteBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import id.herdroid.newsapp.presentation.view.article.ArticleAdapter


@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ArticleAdapter { article ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(article)
            findNavController().navigate(action)
        }

        binding.rvFavorites.adapter = adapter

        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            binding.progressBar.visibility = View.GONE
            if (list.isNullOrEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvFavorites.visibility = View.VISIBLE
                adapter.submitList(list)
            }
        }

        initSwipeToDelete()
        showLoading(true)
        viewModel.loadFavorites()
    }

    private fun initSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val article = adapter.currentList[viewHolder.bindingAdapterPosition]
                viewModel.removeFavorite(article)
                Toast.makeText(requireContext(), "Dihapus dari favorit", Toast.LENGTH_SHORT).show()
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.rvFavorites)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvFavorites.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvEmptyState.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

