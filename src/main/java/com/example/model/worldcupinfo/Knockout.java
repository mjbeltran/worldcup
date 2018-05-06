package com.example.model.worldcupinfo;

public class Knockout
{
    private Round_2 round_2;

    private Round_4 round_4;

    private Round_2_loser round_2_loser;

    private Round_8 round_8;

    private Round_16 round_16;

    public Round_2 getRound_2 ()
    {
        return round_2;
    }

    public void setRound_2 (Round_2 round_2)
    {
        this.round_2 = round_2;
    }

    public Round_4 getRound_4 ()
    {
        return round_4;
    }

    public void setRound_4 (Round_4 round_4)
    {
        this.round_4 = round_4;
    }

    public Round_2_loser getRound_2_loser ()
    {
        return round_2_loser;
    }

    public void setRound_2_loser (Round_2_loser round_2_loser)
    {
        this.round_2_loser = round_2_loser;
    }

    public Round_8 getRound_8 ()
    {
        return round_8;
    }

    public void setRound_8 (Round_8 round_8)
    {
        this.round_8 = round_8;
    }

    public Round_16 getRound_16 ()
    {
        return round_16;
    }

    public void setRound_16 (Round_16 round_16)
    {
        this.round_16 = round_16;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [round_2 = "+round_2+", round_4 = "+round_4+", round_2_loser = "+round_2_loser+", round_8 = "+round_8+", round_16 = "+round_16+"]";
    }
}
