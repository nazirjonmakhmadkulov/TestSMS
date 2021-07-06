package com.nazirjon.testsms.ui.code

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.currencywatcher.data.api.RemoteService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CodeFragmentViewModel : ViewModel() {

    private val remoteService: RemoteService = RemoteService()
    var token: MutableLiveData<String> = MutableLiveData()

    // Метод получить token
    fun getauthClients(phone: String, code: String) {

        val res = remoteService.serviceBuilder.authenticateClients(phone, code)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ authenticateClients ->
                token.value = authenticateClients.token
                Log.d(
                    "CodeFragmentViewModel",
                    "Success called in the getauthClients API method for the date requested: ${authenticateClients.token}"
                )
            },
                { throwable ->
                    Log.d(
                        "CodeFragmentViewModel",
                        "onError called in the getauthClients API method: ${throwable.printStackTrace()}"
                    )
                })

    }
}