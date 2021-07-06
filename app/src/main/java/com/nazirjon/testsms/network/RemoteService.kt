package com.desiredsoftware.currencywatcher.data.api

class RemoteService () {
    val BASE_URL : String = "https://dev.pulttaxi.ru/api/"
    val serviceBuilder = ServiceBuilder.create(BASE_URL)
}