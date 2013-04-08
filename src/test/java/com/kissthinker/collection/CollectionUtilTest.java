package com.kissthinker.collection;

import static com.kissthinker.collection.CollectionUtil.arrayList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * @author David Ainslie
 *
 */
public class CollectionUtilTest
{
    /**
     * 
     */
    @Test
    public void listToString()
    {
        List<Integer> integers = arrayList(1, 3, 5);
        assertEquals("1\n3\n5", CollectionUtil.toString(integers));
    }
}