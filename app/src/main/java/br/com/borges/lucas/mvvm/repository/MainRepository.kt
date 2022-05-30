package br.com.borges.lucas.mvvm.repository

import br.com.borges.lucas.mvvm.rest.RetrofitService

//repository da tela main - MainActivity - Padrão de projeto repository
class MainRepository constructor(private val retrofitService: RetrofitService) {

  //abstrair a chamada do retrofitservice para um repositório
  fun getAllLives() = retrofitService.getAllLives()
}