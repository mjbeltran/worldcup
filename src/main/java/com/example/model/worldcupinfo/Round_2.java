package com.example.model.worldcupinfo;

public class Round_2
{
    private Matches[] matches;

    private String name;

    public Matches[] getMatches ()
    {
        return matches;
    }

    public void setMatches (Matches[] matches)
    {
        this.matches = matches;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [matches = "+matches+", name = "+name+"]";
    }
}