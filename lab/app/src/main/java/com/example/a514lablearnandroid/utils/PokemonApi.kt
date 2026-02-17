package com.example.a514lablearnandroid.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// --- ส่วนที่ 1: Data Model (โครงสร้างข้อมูลตาม JSON) ---
// โครงสร้าง JSON ของ PokeAPI: https://pokeapi.co/api/v2/pokedex/2/
data class PokedexResponse(
    val pokemon_entries: List<PokemonEntry>
)

data class PokemonEntry(
    val entry_number: Int,
    val pokemon_species: PokemonSpecies
)

data class PokemonSpecies(
    val name: String,
    val url: String
)


// --- ส่วนที่ 2: API Interface (เมนูสั่งอาหาร) ---
interface PokemonApiService {
    @GET("pokedex/2/") // Endpoint ที่เราจะเรียก
    suspend fun getKantoPokedex(): PokedexResponse
}

// --- ส่วนที่ 3: Singleton Instance (ตัวจัดการการเชื่อมต่อ) ---
object PokemonNetwork {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    // สร้างตัว Retrofit เพียงครั้งเดียวเพื่อประหยัดทรัพยากร
    val api: PokemonApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // ตัวแปลง JSON
            .build()
            .create(PokemonApiService::class.java)
    }
}