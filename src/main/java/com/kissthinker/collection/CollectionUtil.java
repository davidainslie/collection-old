package com.kissthinker.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rits.cloning.Cloner;

/**
 * Utility for collection functionality.
 * <br/>
 * @author David Ainslie
 */
public abstract class CollectionUtil
{
    /**
     * Ordering of collections may matter.
     * <br/>
     * This {@link CollectionUtil} takes the unusual approach that the default does not care about ordering.
     * 
     * @author David Ainslie
     *
     */
    public enum Order
    {
        /** */
        MATTERS,
        
        /** Default */
        DOES_NOT_MATTER
    }
    
    /**
     * Create an {@link ArrayList} with zero or more "objects".
     * @param objects
     * @return ArrayList<O>
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
     * Create an {@link ArrayList} from a given {@link Collection}
     * @param <O>
     * @param source
     * @return ArrayList<O>
     */
    public static <O> ArrayList<O> arrayList(Collection<O> source)
    {
        return new ArrayList<>(source);
    }

    /**
     * Create a {@link CopyOnWriteArrayList} with zero or more "objects".
     * @param <O>
     * @return CopyOnWriteArrayList<O>
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
     * Create a {@link CopyOnWriteArrayList} from a given {@link Collection}
     * @param source
     * @return CopyOnWriteArrayList<O>
     */
    public static <O> CopyOnWriteArrayList<O> copyOnWriteArrayList(Collection<O> source)
    {
        return new CopyOnWriteArrayList<>(source);
    }
    
    /**
     * Order does not matter by default.
     * @param array1
     * @param array2
     * @return true if given arrays are equal
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
     * @return true if given arrays are equal
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
     * @return true if given collections are equal
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
     * @return true if given collections are equal
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
     * Remove duplicates from given collection
     * @param collection
     * @return C collection without duplicates
     */
    public static <C extends Collection<O>, O> C removeDuplicates(C collection)
    {        
        try
        {
            @SuppressWarnings("unchecked")
            C newCollection = (C)collection.getClass().newInstance();
            
            for (O object : collection)
            {
                if (!newCollection.contains(object))
                {
                    newCollection.add(object);
                }
            }
            
            return newCollection;
        }
        catch (Exception e)
        {
            return collection;
        }
    }

    /**
     * Flatten a given collection i.e. nested collections are flatten to produce just one level within a collection
     * @param collection
     * @return C
     */
    @SuppressWarnings("unchecked")
    public static <C extends Collection<O>, O> C flatten(Collection<?> collection)
    {
        boolean flattened = false;
        
        C flattenedCollection = null;
        
        try
        {
            flattenedCollection = (C)collection.getClass().newInstance();

            for (Object object : collection)
            {
                if (object instanceof Collection<?>)
                {
                    flattened = true;
                    C nestedCollection = flatten((Collection<?>)object);
                    flattenedCollection.addAll(nestedCollection);
                }
                else
                {
                    flattenedCollection.add((O)object);
                }
            }
        }
        catch (Exception e)
        {
            
        }

        if (flattened)
        {
            return flattenedCollection;
        }
        else
        {
            return (C)collection;
        }
    }
    
    /**
     * Turn varargs of objects into a list
     * @param <O>
     * @param objects
     * @return List<O>
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
     * @return String
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
     * @return Sting
     */
    public static <O> String toString(Collection<O> objects)
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