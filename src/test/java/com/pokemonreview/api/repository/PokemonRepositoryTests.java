package com.pokemonreview.api.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pokemonreview.api.entities.Pokemon;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {

	@Autowired
	private PokemonRepository pokemonRepository;
	
	@Test
	public void PokemonRepository_SaveAll_ReturnSavedPokemon() {
		
		//Arrange
		Pokemon pokemon = Pokemon.builder()
							.name("pikachu")
							.type("electric")
							.build();
		
		//Act
		Pokemon savedPokemon = pokemonRepository.save(pokemon);
		
		//Assert
		Assertions.assertThat(savedPokemon).isNotNull();
		Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
	}
	
	@Test
	public void PokemonRepository_GetAll_ReturnMoreThenOnePokemon() {
		
		//Arrange
		Pokemon pokemon1 = Pokemon.builder()
								  .name("pikachu")
								  .type("electric")
								  .build();
		Pokemon pokemon2 = Pokemon.builder()
								  .name("pikachu2")
								  .type("electric2")
								  .build();
				
		//Act
		pokemonRepository.save(pokemon1);
		pokemonRepository.save(pokemon2);
		
		List<Pokemon> pokemonList = pokemonRepository.findAll();
		
		//Assert
		Assertions.assertThat(pokemonList).isNotNull();
		Assertions.assertThat(pokemonList.size()).isEqualTo(2);
		
	}
	
	@Test
	public void PokemonRepository_FindById_ReturnPokemon() {
		
		//Arrange
		Pokemon pokemon = Pokemon.builder()
							.name("pikachu")
							.type("electric")
							.build();
		
		//Act
		pokemonRepository.save(pokemon);
		
		Pokemon returnPokemon = pokemonRepository.findById(pokemon.getId()).get();
		
		//Assert
		Assertions.assertThat(returnPokemon).isNotNull();
	}
	
	@Test
	public void PokemonRepository_FindByType_ReturnPokemon() {
		
		//Arrange
		Pokemon pokemon = Pokemon.builder()
							.name("pikachu")
							.type("electric")
							.build();
		
		//Act
		pokemonRepository.save(pokemon);
		
		Pokemon returnPokemon = pokemonRepository.findByType(pokemon.getType()).get();
		
		//Assert
		Assertions.assertThat(returnPokemon).isNotNull();
	}
	
	@Test
	public void PokemonRepository_UpdatePokemon_ReturnPokemon() {
		
		//Arrange
		Pokemon pokemon = Pokemon.builder()
							.name("pikachu")
							.type("electric")
							.build();
		
		//Act
		pokemonRepository.save(pokemon);
		
		Pokemon savedPokemon = pokemonRepository.findById(pokemon.getId()).get();
		savedPokemon.setType("electric");
		savedPokemon.setName("Raichu");
		
		Pokemon updatedPokemon = pokemonRepository.save(savedPokemon);
		
		//Assert
		Assertions.assertThat(updatedPokemon.getName()).isNotNull();
		Assertions.assertThat(updatedPokemon.getType()).isNotNull();
	}
	
	@Test
	public void PokemonRepository_DeletePokemon_ReturnPokemonIsEmpty() {
		
		//Arrange
		Pokemon pokemon = Pokemon.builder()
							.name("pikachu")
							.type("electric")
							.build();
		
		//Act
		pokemonRepository.save(pokemon);
		
		pokemonRepository.deleteById(pokemon.getId());
		
		Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());
		
		//Assert
		Assertions.assertThat(pokemonReturn).isEmpty();
	}
	
}










