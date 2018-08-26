package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "match")
public class Match {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="match_id")
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="home_team_id")
	private Team homeTeam;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="away_team_id")
	private Team awayTeam;
	@Column(name="stadium")
	private String stadium;
	@Column(name="date")
	private Date date;
	
	@Column(name="home_result")
	private int home_result;
	
	@Column(name="away_result")
	private int away_result;
	
	@Column(name="groupo")
	private String groupo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Team getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}
	public Team getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}
	public String getStadium() {
		return stadium;
	}
	public void setStadium(String stadium) {
		this.stadium = stadium;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getHome_result() {
		return home_result;
	}
	public void setHome_result(int home_result) {
		this.home_result = home_result;
	}
	public int getAway_result() {
		return away_result;
	}
	public void setAway_result(int away_result) {
		this.away_result = away_result;
	}

	public String getGroupo() {
		return groupo;
	}
	public void setGroupo(String groupo) {
		this.groupo = groupo;
	}
	
}
