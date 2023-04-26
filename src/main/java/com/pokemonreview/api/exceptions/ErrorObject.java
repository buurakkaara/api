package com.pokemonreview.api.exceptions;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorObject {

	private int statusCode;
	private String message;
	private Date timestamp;
	
}
