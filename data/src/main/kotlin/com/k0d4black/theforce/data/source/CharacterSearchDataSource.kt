package com.k0d4black.theforce.data.source

import com.k0d4black.theforce.data.api.StarWarsApiService
import com.k0d4black.theforce.data.mappers.toEntity
import com.k0d4black.theforce.data.models.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterSearchDataSource(private val apiService: StarWarsApiService) {

    /**
     * Takes in a [characterName] to be used for the search
     *
     * @return [Flow] of starwars characters transformed to entities that can be used in the
     * data layer.
     */
    suspend fun query(characterName: String): Flow<List<CharacterEntity>> {
        val searchResponse = apiService.searchCharacters(characterName)
        val starWarsCharacters = mutableListOf<CharacterEntity>()
        for (starWarsCharacter in searchResponse.results) {
            starWarsCharacters.add(starWarsCharacter.toEntity())
        }
        return flow { emit(starWarsCharacters) }
    }

}