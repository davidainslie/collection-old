package com.kissthinker.collection.event;

/**
 *
 * @author David Ainslie
 * @param <O>
 */
public class MoveEvent<O>
{
    /** */
    private final O object;
    
    /** */
    private final int toIndex;

    /**
     *
     * @param object
     * @param toIndex
     */
    public MoveEvent(O object, int toIndex)
    {
        this.object = object;
        this.toIndex = toIndex;
    }

    /**
     *
     * @return
     */
    public O object()
    {
        return object;
    }

    /**
     *
     * @return
     */
    public int toIndex()
    {
        return toIndex;
    }
}