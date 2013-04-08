package com.kissthinker.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author David Ainslie
 */
public abstract class CollectionUtil
{
    /**
     *
     * @param <O>
     * @return
     */
    public static <O> ArrayList<O> arrayList()
    {
        return new ArrayList<>();
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
    public static <O> CopyOnWriteArrayList<O> copyOnWriteArrayList()
    {
        return new CopyOnWriteArrayList<>();
    }

    /**
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isEqual(int[] array1, int[] array2)
    {
        if (array1 == null || array2 == null)
        {
            return false;
        }

        Arrays.sort(array1);
        Arrays.sort(array2);

        return Arrays.equals(array1, array2);
    }

    /**
     *
     * @param <O>
     * @param list1
     * @param list2
     * @return
     */
    public static <O> boolean isEqual(List<O> list1, List<O> list2)
    {
        if (list1.size() != list2.size())
        {
            return false;
        }

        for (int i = 0; i < list1.size(); i++)
        {
            if (!list1.get(i).equals(list2.get(i)))
            {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param ints
     * @param checkInt
     * @return
     */
    public static boolean contains(int[] ints, int checkInt)
    {
        for (int i : ints)
        {
            if (i == checkInt)
            {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param collection
     * @return
     */
    public static int[] toIntArray(Collection<?> collection)
    {
        int[] result = new int[collection.size()];
        int i = 0;

        for (Object object : collection)
        {
            result[i++] = new Integer(object.toString());
        }

        return result;
    }

    /**
     *
     * @param collection
     * @return
     */
    public static double[] toDoubleArray(Collection<?> collection)
    {
        double[] result = new double[collection.size()];
        int i = 0;

        for (Object object : collection)
        {
            result[i++] = new Double(object.toString());
        }

        return result;
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
            newList = new ArrayList<O>();
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