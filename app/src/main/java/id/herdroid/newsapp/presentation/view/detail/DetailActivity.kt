package id.herdroid.newsapp.presentation.view.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.newsapp.R
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.databinding.ActivityDetailBinding

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        article = intent.getParcelableExtra("article") // Assuming the article is passed via Intent

        setupWebView()
        setupFavoriteButton()
        setupToolbar()

        article?.let {
            viewModel.checkIsFavorite(it.url)
        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.visibility = View.GONE
                    binding.webView.visibility = View.VISIBLE
                }
            }
            article?.let {
                loadUrl(it.url)
            }
        }
    }

    private fun setupFavoriteButton() {
        viewModel.isFavorite.observe(this) { isFavorite ->
            binding.favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
            )
        }

        binding.favoriteButton.setOnClickListener {
            article?.let {
                val isNowFavorite = viewModel.toggleFavorite(it)
                if (isNowFavorite) {
                    Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
