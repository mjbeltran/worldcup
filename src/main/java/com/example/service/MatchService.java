package com.example.service;

import java.util.List;

import com.example.model.Match;

public interface MatchService {

	public void saveMatch(Match match);
	
	public List<Match> findAll();
	
	public List<Match> findByStadium(String stadium);

}
