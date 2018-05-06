package com.example.model.worldcupinfo;

public class D
{
    private Matches[] matches;

    private String runnerup;

    private String winner;

    public Matches[] getMatches ()
    {
        return matches;
    }

    public void setMatches (Matches[] matches)
    {
        this.matches = matches;
    }

    public String getRunnerup ()
    {
        return runnerup;
    }

    public void setRunnerup (String runnerup)
    {
        this.runnerup = runnerup;
    }

    public String getWinner ()
    {
        return winner;
    }

    public void setWinner (String winner)
    {
        this.winner = winner;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [matches = "+matches+", runnerup = "+runnerup+", winner = "+winner+"]";
    }
}
			
			