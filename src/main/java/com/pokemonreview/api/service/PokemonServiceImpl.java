package com.pokemonreview.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.entities.Pokemon;
import com.pokemonreview.api.exceptions.PokemonNotFoundException;
import com.pokemonreview.api.repository.PokemonRepository;

@Service
public class PokemonServiceImpl implements PokemonService{

	private PokemonRepository pokemonRepository;
	
	@Autowired
	public PokemonServiceImpl(PokemonRepository pokemonRepository) {
		this.pokemonRepository = pokemonRepository;
	}

	@Override
	public PokemonDto createPokemon(PokemonDto pokemonDto) {
		Pokemon pokemon = new Pokemon();
		pokemon.setName(pokemonDto.getName());
		pokemon.setType(pokemonDto.getType());
		
		Pokemon newPokemon = pokemonRepository.save(pokemon);
		
		PokemonDto pokemonResponse = new PokemonDto();
		pokemonResponse.setId(newPokemon.getId());
		pokemonResponse.setName(newPokemon.getName());
		pokemonResponse.setType(newPokemon.getType());
		
		return pokemonResponse;
	}

	@Override
	public PokemonResponse getAllPokemon(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
		List<Pokemon> listOfPokemon = pokemons.getContent();
		List<PokemonDto> content = listOfPokemon.stream().map(pokemon -> mapToPokemonDto(pokemon)).collect(Collectors.toList());
		
		PokemonResponse pokemonResponse = new PokemonResponse();
		pokemonResponse.setContent(content);
		pokemonResponse.setPageNo(pokemons.getNumber());
		pokemonResponse.setPageSize(pokemons.getSize());
		pokemonResponse.setTotalElements(pokemons.getTotalElements());
		pokemonResponse.setTotalPages(pokemons.getTotalPages());
		pokemonResponse.setLast(pokemons.isLast());
		
		return pokemonResponse;
	}
	
	@Override
	public PokemonDto getPokemonById(int id) {
		Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
		return mapToPokemonDto(pokemon);
	}
	
	@Override
	public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
		Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
		pokemon.setName(pokemonDto.getName());
		pokemon.setType(pokemonDto.getType());
		Pokemon updatedPokemon = pokemonRepository.save(pokemon);
		return mapToPokemonDto(updatedPokemon);
	}
	
	@Override
	public void deletePokemon(int id) {
		Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
		pokemonRepository.delete(pokemon);
	}

	private PokemonDto mapToPokemonDto(Pokemon pokemon) {
		PokemonDto pokemonDto = new PokemonDto();
		pokemonDto.setId(pokemon.getId());
		pokemonDto.setName(pokemon.getName());
		pokemonDto.setType(pokemon.getType());
		return pokemonDto;
	}
	
	private Pokemon mapToPokemon(PokemonDto pokemonDto) {
		Pokemon pokemon = new Pokemon();
		pokemon.setName(pokemonDto.getName());
		pokemon.setType(pokemonDto.getType());
		return pokemon;
	}







}






