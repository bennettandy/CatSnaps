package com.avsoftware.data

import com.avsoftware.data.model.BreedDto
import com.avsoftware.data.model.CatImageDto
import com.avsoftware.data.util.NetworkError
import com.avsoftware.data.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class CatClient(
    private val httpClient: HttpClient,
    private val baseUrl: String = "https://api.thecatapi.com/v1/"
) {

    suspend fun getBreeds(): Result<List<BreedDto>, NetworkError> {

        val response = try {
            httpClient.get(
                urlString = "${baseUrl}breeds",
            )
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when(response.status.value) {
            in 200..299 -> {
                val breedList = response.body<List<BreedDto>>()
                Result.Success(breedList)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun getImagesByBreed(limit: Int, page: Int, breedId: String): Result<List<CatImageDto>, NetworkError> {

        val response = try {
            httpClient.get(
                urlString = "${baseUrl}images/search"
            ){
                parameter("has_breeds", "true")
                parameter("size", "small")
                parameter("mime_types", "jpg")
                parameter("format", "json")
                parameter("include_breed", "1")
                parameter("order", "ASC")
                parameter("limit", limit.toString())
                parameter("page", page.toString())
                parameter("breed_ids", breedId)
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when(response.status.value) {
            in 200..299 -> {
                val breedList = response.body<List<CatImageDto>>()
                Result.Success(breedList)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

}