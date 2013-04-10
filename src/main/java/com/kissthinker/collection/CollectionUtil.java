package com.kissthinker.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rits.cloning.Cloner;

/**
 * @author David Ainslie
 */
public abstract class CollectionUtil
{
    public enum Order
    {
        /** */
        MATTERS,
        
        /** Default */
        DOES_NOT_MATTER
    }
    
    /**
     * 
     * @param objects
     * @return
     */
    @SafeVarargs
    public static <O> ArrayList<O> arrayList(O... objects)
    {
        ArrayList<O> list = new ArrayList<>();
        
        for (O object : objects)
        {
            list.add(object);
        }
        
        return list;
    }
    
    /**
     *
     * @param <O>
     * @param source
     * @return
     */
    public static <O> ArrayList<O> arrayList(Collection<O> source)
    {
        return new ArrayList<>(source);
    }

    /**
     *
     * @param <O>
     * @return
     */
    @SafeVarargs
    public static <O> CopyOnWriteArrayList<O> copyOnWriteArrayList(O... objects)
    {        
        CopyOnWriteArrayList<O> list = new CopyOnWriteArrayList<>();
        
        for (O object : objects)
        {
            list.add(object);
        }
        
        return list;
    }

    /**
     * 
     * @param source
     * @return
     */
    public static <O> CopyOnWriteArrayList<O> copyOnWriteArrayList(Collection<O> source)
    {
        return new CopyOnWriteArrayList<>(source);
    }
    
    /**
     * Order does not matter by default.
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isEqual(int[] array1, int[] array2)
    {
        return isEqual(Order.DOES_NOT_MATTER, array1, array2);        
    }

    /**
     * 
     * @param order
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isEqual(Order order, int[] array1, int[] array2)
    {
        if (array1 == null && array2 == null)
        {
            return true;
        }
        else if (array1 == null && array2 != null)
        {
            return false;
        }
        else if (array1 != null && array2 == null)
        {
            return false;
        }

        int[] array1Local = array1.clone();
        int[] array2Local = array2.clone();
        
        if (Order.DOES_NOT_MATTER == order)
        {
            Arrays.sort(array1Local);
            Arrays.sort(array2Local);
        }

        return Arrays.equals(array1Local, array2Local);
    }
    
    /**
     * Order does not matter by default.
     * @param collection1
     * @param collection2
     * @return
     */
    public static <O> boolean isEqual(Collection<O> collection1, Collection<O> collection2)
    {
        return isEqual(Order.DOES_NOT_MATTER, collection1, collection2);
    }
    
    /**
     *
     * @param <O>
     * @param collection1
     * @param collection2
     * @return
     */
    public static <O> boolean isEqual(Order order, Collection<O> collection1, Collection<O> collection2)
    {
        if (collection1 == null && collection2 == null)
        {
            return true;
        }
        else if (collection1 == null && collection2 != null)
        {
            return false;
        }
        else if (collection1 != null && collection2 == null)
        {
            return false;
        }
        else if (collection1.size() != collection2.size())
        {
            return false;
        }

        Cloner cloner = new Cloner();
        
        List<O> list1 = arrayList(cloner.deepClone(collection1));
        List<O> list2 = arrayList(cloner.deepClone(collection2));

        if (Order.MATTERS == order)
        {
            for (int i = 0; i < list1.size(); i++)
            {
                if (!list1.get(i).equals(list2.get(i)))
                {
                    return false;
                }
            }
        }
        else
        {
            for (Iterator<O> i = list1.iterator(); i.hasNext();)
            {
                list2.remove(i.next());
                i.remove();
            }            
            
            if (!list1.isEmpty() || !list2.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param <O>
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <O> List<O> removeDuplicates(List<O> list)
    {
        List<O> newList = null;

        try
        {
            newList = list.getClass().newInstance();
        }
        catch (Exception e)
        {
            newList = new ArrayList<>();
        }

        for (O object : list)
        {
            if (!newList.contains(object))
            {
                newList.add(object);
            }
        }

        return newList;
    }

    /**
     * Flatten a given list.
     * If the given list is just a list of O, then nothing happens and the original is returned.
     * If the given list contains lists (which in turn may contain lists) then a new ArrayList<O> is returned with the nesting of lists removed.
     * @param <O>
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <O> List<O> flatten(List<?> list)
    {
        boolean flattened = false;
        List<O> flattenedList = new ArrayList<O>();

        for (Object object : list)
        {
            if (object instanceof List<?>)
            {
                flattened = true;
                List<O> nestedList = flatten((List<?>)object);
                flattenedList.addAll(nestedList);
            }
            else
            {
                flattenedList.add((O)object);
            }
        }

        if (flattened)
        {
            return flattenedList;
        }
        else
        {
            return (List<O>)list;
        }
    }

    /**
     *
     * @param <O>
     * @param objects
     * @return
     */
    @SafeVarargs
    public static <O> List<O> asList(O... objects)
    {
        return Arrays.asList(objects);
    }

    /**
     *
     * @param <O>
     * @param objects
     * @return
     */
    @SafeVarargs
    public static <O> String toString(O... objects)
    {
        return Arrays.toString(objects);
    }

    /**
     *
     * @param <O>
     * @param objects
     * @return
     */
    public static <O> String toString(List<O> objects)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (O object : objects)
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.append("\n");
            }

            stringBuilder.append(object);
        }

        return stringBuilder.toString();
    }
}