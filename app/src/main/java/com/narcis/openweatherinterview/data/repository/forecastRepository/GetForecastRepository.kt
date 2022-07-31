package com.narcis.openweatherinterview.data.repository.forecastRepository

import com.narcis.openweatherinterview.data.dataSource.IForecastDataSource
import com.narcis.openweatherinterview.data.model.ForecastItem
import com.narcis.openweatherinterview.data.model.ForecastList
import com.narcis.openweatherinterview.data.model.ForecastResponse
import com.narcis.openweatherinterview.data.model.LocationModel
import com.narcis.openweatherinterview.domain.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetForecastRepository @Inject constructor(
    private val iForecastDataSource: IForecastDataSource
) : IGetForecastRepository{
    override fun getForecastRepository(latLong: LocationModel):Flow<ResultWrapper<List<ForecastItem>>> {
        return flow {
           val item =  iForecastDataSource.getForecastDataSource(latLong).mapToForecastItem()
        emit(ResultWrapper.Success(item))}
    }

    private fun ForecastList.mapToForecastItem() : List<ForecastItem> {
        return this.list.map { item ->  ForecastItem(
            temp = item.main.temp,
            temp_min = item.main.tempMin,
            temp_max = item.main.tempMax,
            description = item.weather[0].description
        )
    }
}
}