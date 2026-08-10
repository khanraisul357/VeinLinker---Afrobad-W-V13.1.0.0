package com.afrobad.VeinLinker.config.securityconfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.*;

/*
 *Telling Spring to manage & store object of JWTProperties in IOC Container, it is injected as well.
 */
@Setter
@Getter
@Configuration
//Telling spring go to applicaion.properties, find key start with prefix "jwt" and map those key with variable & map values automatically..
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
	
	//this variable mapped with jwt.secret in application.properties & value of jwt.secret stored in secret.
	private String secret;
	//this variable mapped with jwt.expiration in application.properties & value of jwt.expiration stored in secret.
    private long expiration;


}
