package com.example.a514lablearnandroid

import com.example.a514lablearnandroid.utils.PokedexResponse
import com.example.a514lablearnandroid.utils.PokemonEntry
import com.example.a514lablearnandroid.utils.PokemonSpecies
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for Pokemon Data Models.
 */
class PokemonDataTest {

    @Test
    fun testPokemonSpecies_creation() {
        val name = "Pikachu"
        val url = "https://pokeapi.co/api/v2/pokemon-species/25/"
        val species = PokemonSpecies(name, url)
        
        assertEquals(name, species.name)
        assertEquals(url, species.url)
    }

    @Test
    fun testPokemonEntry_creation() {
        val species = PokemonSpecies("Bulbasaur", "url1")
        val entry = PokemonEntry(1, species)
        
        assertEquals(1, entry.entry_number)
        assertEquals("Bulbasaur", entry.pokemon_species.name)
    }

    @Test
    fun testPokedexResponse_listHandling() {
        val species1 = PokemonSpecies("Charmander", "url4")
        val species2 = PokemonSpecies("Squirtle", "url7")
        val entry1 = PokemonEntry(4, species1)
        val entry2 = PokemonEntry(7, species2)
        
        val response = PokedexResponse(listOf(entry1, entry2))
        
        assertEquals(2, response.pokemon_entries.size)
        assertEquals("Charmander", response.pokemon_entries[0].pokemon_species.name)
        assertEquals(7, response.pokemon_entries[1].entry_number)
    }
}
