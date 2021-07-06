package com.nazirjon.testsms.ui.info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.currencywatcher.data.api.RemoteService
import com.nazirjon.testsms.model.InfoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class InfoFragmentViewModel : ViewModel() {

    private val remoteService: RemoteService = RemoteService()
    var infoResponse: MutableLiveData<InfoResponse> = MutableLiveData()

    // Метод получить get info
    fun getInfo(token: String) {
        val info : InfoResponse
        val res = remoteService.serviceBuilder.getInfo(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ getInfo ->
                infoResponse.value = getInfo
                Log.d(
                    "InfoFragmentViewModel",
                    "Success called in the getInfo API method for the date requested: ${getInfo}"
                )
            },
                { throwable ->
                    Log.d(
                        "InfoFragmentViewModel",
                        "onError called in the getInfo API method: ${throwable.printStackTrace()}"
                    )
                })

    }
}