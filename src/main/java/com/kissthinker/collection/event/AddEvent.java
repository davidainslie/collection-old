package com.kissthinker.collection.event;

/**
 *
 * @author David Ainslie
 * @param <O>
 */
public class AddEvent<O>
{
    /** */
    private final O[] objects;

    /**
     *
     * @param objects
     */
    @SafeVarargs
    public AddEvent(O... objects)
    {
        this.objects = objects;
    }

    /**
     *
     */
    public O[] objects()
    {
        return objects;
    }
}