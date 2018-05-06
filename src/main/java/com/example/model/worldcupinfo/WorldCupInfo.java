package com.example.model.worldcupinfo;


public class WorldCupInfo
{
    private Teams[] teams;

    private Tvchannels[] tvchannels;

    private Stadiums[] stadiums;

    private Knockout knockout;

    private Groups groups;

    public Teams[] getTeams ()
    {
        return teams;
    }

    public void setTeams (Teams[] teams)
    {
        this.teams = teams;
    }

    public Tvchannels[] getTvchannels ()
    {
        return tvchannels;
    }

    public void setTvchannels (Tvchannels[] tvchannels)
    {
        this.tvchannels = tvchannels;
    }

    public Stadiums[] getStadiums ()
    {
        return stadiums;
    }

    public void setStadiums (Stadiums[] stadiums)
    {
        this.stadiums = stadiums;
    }

    public Knockout getKnockout ()
    {
        return knockout;
    }

    public void setKnockout (Knockout knockout)
    {
        this.knockout = knockout;
    }

    public Groups getGroups ()
    {
        return groups;
    }

    public void setGroups (Groups groups)
    {
        this.groups = groups;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [teams = "+teams+", tvchannels = "+tvchannels+", stadiums = "+stadiums+", knockout = "+knockout+", groups = "+groups+"]";
    }
}
