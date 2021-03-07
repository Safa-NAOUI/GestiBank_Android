package com.gestibank.application.api

import com.gestibank.application.api.RetrofitClient.getClient


object APIService {

    private fun APIService() {}

    const val API_URL = "http://192.168.0.17:86/"
    const val API_CURRENCY = "http://api.currencylayer.com/"

    fun getClientService(): ClientService? {
        return getClient(API_URL)!!.create(ClientService::class.java)
    }


    fun getCurrencyService(): ClientService {
        return getClient(API_CURRENCY)?.create(ClientService::class.java)!!
    }


}