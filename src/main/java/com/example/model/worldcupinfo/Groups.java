package com.example.model.worldcupinfo;

import java.io.Serializable;
import java.util.List;

public class Groups implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private F f;

    private G g;

    private D d;

    private E e;

    private B b;

    private C c;

    private A a;

    private H h;
    
    private List<Grupo> listeGroup;

    public F getF ()
    {
        return f;
    }

    public void setF (F f)
    {
        this.f = f;
    }

    public G getG ()
    {
        return g;
    }

    public void setG (G g)
    {
        this.g = g;
    }

    public D getD ()
    {
        return d;
    }

    public void setD (D d)
    {
        this.d = d;
    }

    public E getE ()
    {
        return e;
    }

    public void setE (E e)
    {
        this.e = e;
    }

    public B getB ()
    {
        return b;
    }

    public void setB (B b)
    {
        this.b = b;
    }

    public C getC ()
    {
        return c;
    }

    public void setC (C c)
    {
        this.c = c;
    }

    public A getA ()
    {
        return a;
    }

    public void setA (A a)
    {
        this.a = a;
    }

    public H getH ()
    {
        return h;
    }

    public void setH (H h)
    {
        this.h = h;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [f = "+f+", g = "+g+", d = "+d+", e = "+e+", b = "+b+", c = "+c+", a = "+a+", h = "+h+"]";
    }

	public List<Grupo> getListeGroup() {
		return listeGroup;
	}

	public void setListeGroup(List<Grupo> listeGroup) {
		this.listeGroup = listeGroup;
	}
}
