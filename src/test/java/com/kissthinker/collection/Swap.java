package com.kissthinker.collection;

/**
 * @author David Ainslie
 */
public class Swap implements Instrument
{
    /** */
    private final String tenor;
    
    /** */
    private final String currency;

    /**
     *
     * @param tenor
     * @param currency
     */
    public Swap(String tenor, String currency)
    {
        this.tenor = tenor;
        this.currency = currency;
    }

    /**
     *
     */
    @Override
    public String toString()
    {
        return "Swap [tenor=" + tenor + ", currency=" + currency + "]";
    }

    /**
     *
     */
    @Override
    public Class<? extends Instrument> getType()
    {
        return getClass();
    }

    /**
     *
     * @return
     */
    public String getTenor()
    {
        return tenor;
    }

    /**
     *
     * @return
     */
    public String getCurrency()
    {
        return currency;
    }
}