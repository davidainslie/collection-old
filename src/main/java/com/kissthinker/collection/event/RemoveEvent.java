package com.kissthinker.collection.event;

/**
 *
 * @author David Ainslie
 * @param <O>
 */
public class RemoveEvent<O>
{
    /** */
    private final O[] objects;

    /**
     *
     * @param objects
     */
    @SafeVarargs
    public RemoveEvent(O... objects)
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