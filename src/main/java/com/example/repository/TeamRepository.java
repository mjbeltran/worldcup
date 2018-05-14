package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Team;

@Repository("teamRepository")
public interface TeamRepository extends JpaRepository<Team, Long> {

	 Team findByName(String name);
}
