package com.kissthinker.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.op4j.Op;
import org.op4j.functions.ExecCtx;
import org.op4j.functions.Get;
import org.op4j.functions.IFunction;

import com.kissthinker.reflect.ConstructorUtil;
import com.kissthinker.reflect.ObjectUtil;

/**
 * @author David Ainslie
 */
public abstract class Sorter
{
    /** */
    public static final String CLASS_SORT = "CLASS_SORT";

    /**
     * Any duplicate properties will be removed/ignored.
     * @param <O>
     * @param objects
     * @param properties
     * @return
     */
    public static <O> List<O> sort(List<O> objects, String... properties)
    {
        return sort(objects, Arrays.asList(properties));
    }

    /**
     * Any duplicate properties will be removed/ignored.
     * @param <O>
     * @param objects
     * @param properties
     * @return
     */
    public static <O> List<O> sort(List<O> objects, List<String> properties)
    {
        LinkedHashSet<String> propertiesSet = new LinkedHashSet<String>();

        for (String property : properties)
        {
            propertiesSet.add(property);
        }

        return sort(objects, propertiesSet);
    }

    /**
     *
     * @param <O>
     * @param objects
     * @param properties a LinkedHashSet as opposed to a more generic Set, as the property insertion order is exactly the order of sorting.
     * @return
     */
    public static <O> List<O> sort(List<O> objects, LinkedHashSet<String> properties)
    {
        if (properties == null || properties.isEmpty())
        {
            return objects;
        }

        // Accumulation of sorts.
        List<List<O>> sortedObjects = ConstructorUtil.newInstance(objects.getClass());

        // Perform a sort per property.
        String property = properties.iterator().next();

        if (CLASS_SORT.equals(property))
        {
            TreeMap<Object, List<O>> sortedMap = new TreeMap<Object, List<O>>();

            for (O object : objects)
            {
                String key = ObjectUtil.fullClassName(object);
                List<O> sortedList = sortedMap.get(key);

                if (sortedList == null)
                {
                    sortedList = ConstructorUtil.newInstance(objects.getClass());
                    sortedMap.put(key, sortedList);
                }

                sortedList.add(object);
            }

            return sort(sortedObjects, sortedMap, properties);
        }
        else
        {
            Map<Object, List<O>> sortedMap = Op.on(objects).zipAndGroupKeysBy(new PropertyGetterFunction(property)).get();

            return sort(sortedObjects, sortedMap, properties);
        }
    }

    /**
     *
     * @param <O>
     * @param sortedObjects
     * @param sortedMap
     * @param properties
     * @return
     */
    private static <O> List<O> sort(List<List<O>> sortedObjects, Map<Object, List<O>> sortedMap, LinkedHashSet<String> properties)
    {
        for (List<O> objectList : sortedMap.values())
        {
            // Copy of passed in properties as next "pass" will have current "property" removed.
            List<String> sortingProperties = new ArrayList<String>(properties.size());

            for (String sortingProperty : properties)
            {
                sortingProperties.add(sortingProperty);
            }

            sortingProperties.remove(0);

            sortedObjects.add(sort(objectList, sortingProperties));
        }

        return CollectionUtil.flatten(sortedObjects);
    }
}

/**
 * Must handle the case when a property does not exist.
 * All objects are grouped together when a property does not exist i.e. they are omitted from the current property sort.
 * @author David Ainslie
 */
class PropertyGetterFunction implements IFunction<Object, Object>
{
    /** */
    String property;

    /**
     *
     */
    @Override
    public Object execute(Object object, ExecCtx execCtx) throws Exception
    {
        try
        {
            return Get.attrOfObject(property).execute(object);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     *
     * @param property
     */
    PropertyGetterFunction(String property)
    {
        this.property = property;
    }
}