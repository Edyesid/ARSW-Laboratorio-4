/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {
	
    @Autowired
    CinemaServices csp;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorRecursoCinemas() {
    	try {
    		Set<Cinema> data = csp.getAllCinemas();
    		return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
    	} catch (CinemaPersistenceException e) {
    		Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
    	}
    }
    
    
    @RequestMapping(method = RequestMethod.GET,path = "/{name}")
    public ResponseEntity<?> manejadorRecursoCinema(@PathVariable("name") String name) {
    	try {
    		Cinema data = csp.getCinemaByName(name);
    		return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
 	
    	} catch (CinemaException e) {
    		Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
    	}
    }
    
    @RequestMapping(method = RequestMethod.GET,path = "/{name}/{date}")
    public ResponseEntity<?> manejadorRecursoCinemaDate(@PathVariable("name") String name, @PathVariable("date") String date) {
    	try {
    		List<CinemaFunction> data = csp.getFunctionsbyCinemaAndDate(name, date);
    		return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
 	
    	} catch (CinemaException e) {
    		Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
    	}
    }
}
