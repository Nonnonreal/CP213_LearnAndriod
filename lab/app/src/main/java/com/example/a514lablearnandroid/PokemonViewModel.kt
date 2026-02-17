package com.example.a514lablearnandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a514lablearnandroid.utils.PokemonEntry
import com.example.a514lablearnandroid.utils.PokemonNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    // State สำหรับบอกสถานะของหน้าจอ (Loading, Success, Error)
    // ในที่นี้เอาแบบง่ายสุดคือเก็บ List ของโปเกมอน
    private val _pokemonList = MutableStateFlow<List<PokemonEntry>>(emptyList())
    val pokemonList = _pokemonList.asStateFlow()

    // ฟังก์ชันยิง API
    fun fetchPokemon() {
        viewModelScope.launch {
            try {
                // เรียกใช้ API จากไฟล์ PokemonApi.kt ที่เราสร้าง
                val response = PokemonNetwork.api.getKantoPokedex()

                // อัปเดตข้อมูลใส่ State
                _pokemonList.value = response.pokemon_entries

            } catch (e: Exception) {
                // จัดการ Error (เช่น Log หรือโชว์ Toast)
                e.printStackTrace()
            }
        }
    }
}