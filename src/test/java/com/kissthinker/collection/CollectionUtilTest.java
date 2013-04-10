package com.kissthinker.collection;

import static com.kissthinker.collection.CollectionUtil.arrayList;
import static com.kissthinker.collection.CollectionUtil.flatten;
import static com.kissthinker.collection.CollectionUtil.isEqual;
import static com.kissthinker.collection.CollectionUtil.removeDuplicates;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kissthinker.collection.CollectionUtil.Order;

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
    public void arrayListCreation()
    {
        List<Integer> integers = arrayList();
        assertEquals(0, integers.size());
    }
    
    /**
     * 
     */
    @Test
    public void arrayListFromList()
    {
        @SuppressWarnings("serial")
        List<Integer> list = new ArrayList<Integer>() {{ add(3); add(5); }};
        
        List<Integer> integers = arrayList(list);
        assertEquals(2, integers.size());
    }
    
    /**
     * 
     */
    @Test
    public void arrayListToString()
    {
        List<Integer> integers = arrayList(1, 3, 5);
        assertEquals("1\n3\n5", CollectionUtil.toString(integers));
    }
    
    /**
     * 
     */
    @Test
    public void nullArraysAreEqual()
    {
        assertTrue(isEqual((int[])null, (int[])null));
    }
    
    /**
     * 
     */
    @Test
    public void arrayAndNullArrayAreNotEqual()
    {
        assertFalse(isEqual(new int[] {7, 9}, null));
    }
    
    /**
     * 
     */
    @Test
    public void arraysInSameOrderAreEqual()
    {
        assertTrue(isEqual(new int[] {7, 9}, new int[] {7, 9}));
    }
    
    /**
     * 
     */
    @Test
    public void arraysInDifferentOrderAreEqual()
    {
        assertTrue(isEqual(new int[] {7, 9}, new int[] {9, 7}));
    }
    
    /**
     * 
     */
    @Test
    public void arraysInDifferentOrderWhereOrderMattersAreNotEqual()
    {
        assertFalse(isEqual(Order.MATTERS, new int[] {7, 9}, new int[] {9, 7}));
    }
    
    /**
     * 
     */
    @Test
    public void nullListsAreEqual()
    {
        assertTrue(isEqual((List<Object>)null, (List<Object>)null));
    }
    
    /**
     * 
     */
    @Test
    public void listAndNullListAreNotEqual()
    {
        assertFalse(isEqual(arrayList(4, 5), null));
    }
    
    /**
     * 
     */
    @Test
    public void listsInSameOrderAreEqual()
    {
        assertTrue(isEqual(arrayList(4, 5), arrayList(4, 5)));
    }
    
    /**
     * 
     */
    @Test
    public void listsInDifferentOrderAreEqual()
    {
        assertTrue(isEqual(arrayList(5, 4), arrayList(4, 5)));
    }
    
    /**
     * 
     */
    @Test
    public void listsInDifferentOrderWhereOrderMattersAreNotEqual()
    {
        assertFalse(isEqual(Order.MATTERS, arrayList(5, 4), arrayList(4, 5)));
    }
    
    /**
     * 
     */
    @Test
    public void removeDuplictes()
    {
        assertEquals(arrayList(1, 3, 5, 7, 8), removeDuplicates(arrayList(1, 3, 5, 3, 3, 7, 8, 1)));
    }
    
    /**
     * 
     */
    @Test
    public void flattenArrayList()
    {
        assertEquals(arrayList(1, 3, 5, 7, 9), flatten(arrayList(1, 3, 5, arrayList(7, 9))));
    }
}