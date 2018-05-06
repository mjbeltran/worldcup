package com.example.model.worldcupinfo;

public class Tvchannels
{
    private String id;

    private String icon;

    private String name;

    private String iso2;

    private String[] lang;

    private String country;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getIso2 ()
    {
        return iso2;
    }

    public void setIso2 (String iso2)
    {
        this.iso2 = iso2;
    }

    public String[] getLang ()
    {
        return lang;
    }

    public void setLang (String[] lang)
    {
        this.lang = lang;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", icon = "+icon+", name = "+name+", iso2 = "+iso2+", lang = "+lang+", country = "+country+"]";
    }
}
