package com.cts.authorization.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ankit pathak
 *
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JwtResponse {

	private String jwttoken;
}