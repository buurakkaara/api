package com.pokemonreview.api.service;

import java.util.List;

import com.pokemonreview.api.dto.ReviewDto;

public interface ReviewService {

	ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
	
	List<ReviewDto> getReviewsByPokemonId(int pokemonId);

	ReviewDto getReviewById(int reviewId, int pokemonId);
	
	ReviewDto updateReview(int reviewId, int pokemonId, ReviewDto reviewDto);

	void deleteReview(int reviewId, int pokemonId);
}
