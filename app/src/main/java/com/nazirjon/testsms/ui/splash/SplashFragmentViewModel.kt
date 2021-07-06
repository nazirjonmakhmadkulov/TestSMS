package com.nazirjon.testsms.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.currencywatcher.data.api.RemoteService
import com.nazirjon.testsms.model.InfoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class SplashFragmentViewModel : ViewModel() {

    private val remoteService: RemoteService = RemoteService()
    var infoResponse: MutableLiveData<InfoResponse> = MutableLiveData()

    // Метод получить get info
    fun getInfo(token: String) {
        val res = remoteService.serviceBuilder.getInfo(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ getInfo ->
                infoResponse.value = getInfo
                Log.d(
                    "SplashFragmentViewModel",
                    "Success called in the getInfo API method for the date requested: ${getInfo}"
                )
            },
                { throwable ->
                    Log.d(
                        "SplashFragmentViewModel",
                        "onError called in the getInfo API method: ${throwable.printStackTrace()}"
                    )
                })

    }
}