package com.example.mtstest1.network

import android.util.Log
import com.example.mtstest1.Models.Author
import com.example.mtstest1.Models.AuthorList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit


object NetClient {
    init {
        createApi()
    }

    //Работа с сетью
    private val TAG: String = "NetClientLOG"
    private val BASE_URL = "https://reststop.randomhouse.com/resources"
    var routes: Api? = null

    //Инициализация нетклиента
    fun createApi() {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder().addHeader("Accept","application/json")
                chain.proceed(builder.build())
            }
            .readTimeout(30, TimeUnit.SECONDS) //После этого времени сработает замыкание - ошибка запроса
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://reststop.randomhouse.com/resources/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        routes = retrofit.create(Api::class.java)
    }

    fun getAuthorList(searchString: String,dataReady: ((AuthorList?,String?) -> Unit)?){
        Log.d(TAG, "Ищем авторов по имени $searchString")
        routes?.getAuthorList(searchString)?.enqueue(getCallBack(dataReady))
    }

    fun getAuthorById(authorId: String,dataReady: ((Author?,String?) -> Unit)?){
        Log.d(TAG, "Ищем авторов по id $authorId")
        routes?.getAuthorById(authorId)?.enqueue(getCallBack(dataReady))
    }

    private fun <T> getCallBack(dataReady: ((T?,String?) -> Unit)?): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                //Запрос успешный
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        Log.d(TAG, "Запрос удачный, и тело ОК!")
                        Log.d(TAG, "Тело = " + response.body())
                        dataReady?.invoke(response.body()!!,null)
                    } else {
                        Log.d(TAG, "Запрос удачный, но тело пустое!")
                        dataReady?.invoke(null,"Ошибка в запросе - тело пришло пустое")
                    }
                } else {
                    //Запрос неудачный
                    Log.d(TAG, "Запрос неудачный!")
                    dataReady?.invoke(null, showErrorInfo(response.errorBody(), response.code()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d(TAG, "Запрос вылетел с ошибкой!")
                t.printStackTrace()
                dataReady?.invoke(null,"Ошибка в запросе $t")
            }
        }
    }
    //Метод для показа диалогового окна с информацией об ошибке
    fun showErrorInfo(errorBody: ResponseBody?, code: Int): String? {
        var errorString = ""
        try {
            if (errorBody != null) {
                errorString = errorBody.string()
                Log.d(TAG, "Ошибка запроса = $errorString")
                val jObjError = JSONObject(errorString)
                val error = """
                $code
                ${jObjError.getString("error")}
                """.trimIndent()
                Log.d(TAG, error)
                //InstrumentsAlert.showDialogError(error, null)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            //InstrumentsAlert.showDialogError(errorString, null)
        } catch (e: JSONException) {
            e.printStackTrace()
            //InstrumentsAlert.showDialogError(errorString, null)
        }
        return errorString
    }
}