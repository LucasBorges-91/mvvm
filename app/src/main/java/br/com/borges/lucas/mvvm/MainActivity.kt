package br.com.borges.lucas.mvvm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.borges.lucas.mvvm.adapter.MainAdapter
import br.com.borges.lucas.mvvm.databinding.ActivityMainBinding
import br.com.borges.lucas.mvvm.repository.MainRepository
import br.com.borges.lucas.mvvm.rest.RetrofitService
import br.com.borges.lucas.mvvm.viewmodel.main.MainViewModel
import br.com.borges.lucas.mvvm.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  lateinit var viewModel: MainViewModel

  private val retrofitService = RetrofitService.getInstance()

  private val adapter = MainAdapter { live ->
    openLink(live.link)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = ViewModelProvider(
      this,
      MainViewModelFactory(MainRepository(retrofitService))
    ).get(
      MainViewModel::class.java
    )

    binding.recyclerview.adapter = adapter

  }

  override fun onStart() {
    super.onStart()
    viewModel.liveList.observe(this, Observer { lives ->
      Log.i("Lucas", "onStart")
      adapter.setLiveList(lives)
    })

    viewModel.errorMessage.observe(this, Observer {
      Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    })
  }

  override fun onResume() {
    super.onResume()
    viewModel.getAllLives()
  }

  private fun openLink(link: String) {
    val browserIntent = Intent( Intent.ACTION_VIEW, Uri.parse(link))
    startActivity(browserIntent)
  }
}