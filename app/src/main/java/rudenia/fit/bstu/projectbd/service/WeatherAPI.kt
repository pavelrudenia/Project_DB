package rudenia.fit.bstu.projectbd.service

import rudenia.fit.bstu.projectbd.model.WeatherModel

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=bingöl&APPID=04a42b96398abc8e4183798ed22f9485

    @GET("data/2.5/weather?&units=metric&APPID=31af109fd32dc593cd3b34f6b50abff5")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>

}