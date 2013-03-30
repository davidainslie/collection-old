package com.kissthinker.collection;

/**
 * @author David Ainslie
 */
public class AnonymousInstrument implements Instrument
{
    /**
     *
     */
    public AnonymousInstrument()
    {
    }

    /**
     *
     */
    @Override
    public Class<? extends Instrument> getType()
    {
        return getClass();
    }
}