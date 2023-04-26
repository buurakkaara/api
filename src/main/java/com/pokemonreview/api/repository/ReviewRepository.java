package com.pokemonreview.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokemonreview.api.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

	List<Review> findByPokemonId(int pokemonId);
	
}
