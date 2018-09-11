package fr.univ_lyon1.info.m1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CharManipulatorTest {
    private CharManipulator manipulator;
    
    @Before
    public void setUp() {
	manipulator = new CharManipulator();
    }

    @Test
    public void orderNormalStringTest() {
	assertEquals("A", manipulator.invertOrder("A"));
	assertEquals("DCBA", manipulator.invertOrder("ABCD"));
	assertEquals("321DCBA", manipulator.invertOrder("ABCD123"));
    }

    @Test
    public void orderEmptyStringTest() {
	assertEquals("", manipulator.invertOrder(""));
    }

    @Test
    public void removePatternRemovesOU() {
	assertEquals("cc", manipulator.removePattern("coucou", "ou"));
    }

    @Test
    public void removePatternEmptyPattern() {
	assertEquals("coucou", manipulator.removePattern("coucou", ""));
    }

    @Test
    public void removePatternNestedPatterns() {
	// Unspecified, but we assume that the pattern is replaced repeatedly until the string does not change anymore.
	assertEquals("", manipulator.removePattern("aabb", "ab"));
    }
    // TODO: test invertCase(String string)
}
