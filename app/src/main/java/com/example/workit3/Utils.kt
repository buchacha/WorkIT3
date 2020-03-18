package com.example.workit3

import java.net.HttpURLConnection
import java.net.URL
import io.reactivex.Observable

fun createRequest (url: String) = Observable.create<String> {

    val urlConnection = URL(url).openConnection() as HttpURLConnection
    try {

        urlConnection.connect()

        if (urlConnection.responseCode != HttpURLConnection.HTTP_OK)
            it.onError(RuntimeException(urlConnection.responseMessage))
        else {
            val str = urlConnection.inputStream.bufferedReader().readText()
            it.onNext(str)
        }
    } finally {
        urlConnection.disconnect()
    }
}