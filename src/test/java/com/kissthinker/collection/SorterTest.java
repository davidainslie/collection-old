package com.kissthinker.collection;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author David Ainslie
 */
public class SorterTest
{
    /**
     *
     */
    @Test
    public void sortTenorFirst()
    {
        List<Instrument> instruments = Arrays.asList(new Future("3M", "EUR"), new Swap("3M", "EUR"), new Future("3M", "USD"), new Future("6M", "EUR"), new Swap("6M", "EUR"), new Future("3M", "EUR"));
        System.out.printf("---------------------------- %s%n", "sort - tenor first");
        System.out.println(CollectionUtil.toString(instruments));

        instruments = Sorter.sort(instruments, Sorter.CLASS_SORT, "tenor", "currency");
        System.out.println("----------------------------");
        System.out.println(CollectionUtil.toString(instruments));
        assertEquals("3M", ((Future)instruments.get(1)).getTenor());
        assertEquals("EUR", ((Future)instruments.get(1)).getCurrency());
    }

    /**
     *
     */
    @Test
    public void sortCurrencyFirst()
    {
        List<Instrument> instruments = Arrays.asList(new Future("3M", "EUR"), new Swap("3M", "EUR"), new Future("3M", "USD"), new Future("6M", "EUR"), new Swap("6M", "EUR"), new Future("3M", "EUR"));
        System.out.printf("---------------------------- %s%n", "sort - currency first");
        System.out.println(CollectionUtil.toString(instruments));

        instruments = Sorter.sort(instruments, Sorter.CLASS_SORT, "currency", "tenor");
        System.out.println("----------------------------");
        System.out.println(CollectionUtil.toString(instruments));
        assertEquals("3M", ((Future)instruments.get(1)).getTenor());
        assertEquals("EUR", ((Future)instruments.get(1)).getCurrency());
    }

    /**
     *
     */
    @Test
    public void sortIncludingType()
    {
        List<Instrument> instruments = Arrays.asList(new Future("3M", "EUR"), new Swap("3M", "EUR"), new Future("3M", "USD"), new Future("6M", "EUR"), new Swap("6M", "EUR"), new Future("3M", "EUR"), new AnonymousInstrument());
        System.out.printf("---------------------------- %s%n", "sort - including type");
        System.out.println(CollectionUtil.toString(instruments));

        instruments = Sorter.sort(instruments, "type", "tenor", "currency");
        System.out.println("----------------------------");
        System.out.println(CollectionUtil.toString(instruments));
        assertEquals("3M", ((Future)instruments.get(1)).getTenor());
        assertEquals("EUR", ((Future)instruments.get(1)).getCurrency());
    }
}