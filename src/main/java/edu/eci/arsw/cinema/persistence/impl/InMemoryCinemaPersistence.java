/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service("InMemoryCinemaPersistence")
public class InMemoryCinemaPersistence implements CinemaPersitence {
    
    private final Map<String,Cinema> cinemas=new HashMap<>();

    public InMemoryCinemaPersistence() {
        String functionDate = "2018-12-18";
        String functionDate2 = "2019-12-18";
        
        List<CinemaFunction> functions= new ArrayList<>();
        List<CinemaFunction> functions2= new ArrayList<>();
        
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
        
        CinemaFunction funct3 = new CinemaFunction(new Movie("Batman","Action"),functionDate2);
        CinemaFunction funct4 = new CinemaFunction(new Movie("Mulan","Action"),functionDate2);
        
        functions.add(funct1);
        functions.add(funct2);
        
        functions2.add(funct3);
        functions2.add(funct4);
        
        Cinema c = new Cinema("cinemaX",functions);
        Cinema c2 = new Cinema("CineColombia",functions2);
        
        cinemas.put("cinemaX", c);
        cinemas.put("CineColombia", c2);
    }    

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaPersistenceException, CinemaException {
			Cinema cin = getCinema(cinema);
	    	List<CinemaFunction> fun = cin.getFunctions();
	    	for (int i = 0; i < fun.size(); i++) {
	    		if (fun.get(i).getMovie().getName() == movieName && fun.get(i).getDate() == date) {
	    			fun.get(i).buyTicket(row, col);
	    		}
	    	}
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaPersistenceException{
    	List<CinemaFunction> functionss= new ArrayList<>();	
		Cinema cin = getCinema(cinema);
		List<CinemaFunction> func = cin.getFunctions();
		for (int i = 0; i < func.size(); i++) {
			if (func.get(i).getDate().equals(date)) {
				functionss.add(func.get(i));
			}
		}
		return functionss;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {
        return cinemas.get(name);
    }
    
    @Override
    public Set<Cinema> getAllCinemas() throws CinemaPersistenceException {
    	
    	Set<Cinema> list = new HashSet(cinemas.values());
    	return list;
    }

	@Override
	public CinemaFunction getFunctionByCinemaDateMovie(String name, String date, String moviename) throws CinemaPersistenceException {
		CinemaFunction function = null; 
		Cinema cin = getCinema(name);
		List<CinemaFunction> functions = cin.getFunctions();	
		for (int i = 0; i < functions.size(); i++) {
			CinemaFunction fun = functions.get(i);
			if (fun.getDate().equals(date) && fun.getMovie().getName().equals(moviename)) {
				function = fun;
			}
		}
		return function;
	}
	
	@Override
	public void NewFunction(String name, CinemaFunction funcion) throws CinemaPersistenceException {
		Cinema cinema = getCinema(name);
		cinema.getFunctions().add(funcion);
	}
	
	@Override
	public void SetFunction(String name, CinemaFunction funct) throws CinemaPersistenceException {
		Cinema cinema = getCinema(name);
		List<CinemaFunction> cinemas = cinema.getFunctions();
		for(CinemaFunction funcion:cinemas) {
			if(funcion.getMovie().getName().equals(funct.getMovie().getName())) {
				funcion.setDate(funct.getDate());
				funcion.setMovie(funct.getMovie());
				funcion.setSeats(funct.getSeats());
				}
			
		}
	}
}
