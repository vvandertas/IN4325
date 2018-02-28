package com.vdtas.models.hints;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author vvandertas
 */
public class HintTest {
    private List<Hint> hints;
    private GenericHint genericHint;
    private NoHint noHint;
    private SpecificHint specificHint;

    @Before
    public void setup() {
        hints = new ArrayList<>();
        // Create three hint instances
        genericHint = new GenericHint();
        noHint = new NoHint();
        specificHint = new SpecificHint();

        // and add them to the hints list
        hints.add(genericHint);
        hints.add(noHint);
        hints.add(specificHint);
    }

    @Test
    public void minTest() throws Exception{
        // Make sure noHint has lowest count
        genericHint.increment();
        specificHint.increment();

        Hint minHint = Hint.min(hints);
        assertEquals("Expecting noHint", noHint, minHint);

        minHint = Hint.randomMin(hints);
        assertEquals("Still expecting noHint", noHint, minHint);

        // All counts are equal
        noHint.increment();
        minHint = Hint.min(hints);
        assertEquals("Expecting genericHint", genericHint, minHint);

        // Expecting noHint or hint3
        genericHint.increment();
        minHint = Hint.min(hints);
        assertNotEquals("This should not be genericHint", genericHint, minHint);
    }

    @Test
    public void minRandomTest() throws Exception{
        // Make sure noHint has lowest count
        genericHint.increment();
        specificHint.increment();

        Hint minHint = Hint.randomMin(hints);
        assertEquals("Expecting noHint", noHint, minHint);

        // noHint and specificHint now have lowest count
        noHint.increment();
        genericHint.increment();

        // expecting the result to be anything but genericHint
        minHint = Hint.min(hints);
        assertNotEquals("This should not be genericHint", genericHint, minHint);
    }
}
