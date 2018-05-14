package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Team;
import com.example.repository.TeamRepository;

@Service("teamService")
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	@Override
	public void saveTeam(Team team) {
        teamRepository.save(team);
		
	}

	@Override
	public Team findUserByName(String name) {
		return teamRepository.findByName(name);
	}

}
