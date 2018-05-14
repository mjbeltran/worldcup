package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Match;
import com.example.repository.MatchRepository;

@Service("matchService")
public class MatchServiceImpl implements MatchService {

	
	@Autowired
	private MatchRepository matchRepository;
	
	@Override
	public void saveMatch(Match match) {
		matchRepository.save(match);
	}

	@Override
	public List<Match> findAll() {
		return matchRepository.findAll();
	}

	@Override
	public List<Match> findByStadium(String stadium) {
		return matchRepository.findByStadium(stadium);
	}
	
}
