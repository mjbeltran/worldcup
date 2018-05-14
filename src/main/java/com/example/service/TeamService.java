package com.example.service;

import com.example.model.Team;

public interface TeamService {

	public void saveTeam(Team team);
	
	public Team findUserByName(String name);
}
