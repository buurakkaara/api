package com.pokemonreview.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokemonreview.api.entities.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

	Optional<Pokemon> findByType(String type);
	
}
