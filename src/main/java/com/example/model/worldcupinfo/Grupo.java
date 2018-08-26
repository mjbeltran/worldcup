package com.example.model.worldcupinfo;

import java.io.Serializable;

public class Grupo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3693025287335257193L;

	private Matches[] matches;

	private String runnerup;

	private String winner;

	private String nameGroup;

	public Matches[] getMatches() {
		return matches;
	}

	public void setMatches(Matches[] matches) {
		this.matches = matches;
	}

	public String getRunnerup() {
		return runnerup;
	}

	public void setRunnerup(String runnerup) {
		this.runnerup = runnerup;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getNameGroup() {
		return nameGroup;
	}

	public void setNameGroup(String nameGroup) {
		this.nameGroup = nameGroup;
	}
}
