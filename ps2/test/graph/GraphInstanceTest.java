/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   (See Evernote: Problem Sets)
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    /* Test methods for add(L vertex) * * * * * * * * * * * * * * * * * * * */
    @Test
    public void testAddVertexDNE() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected to add new vertex", graph.add("New"));
        assertEquals("expected graph to contain only the new vertex", 
                new HashSet<String>(Arrays.asList("New")), graph.vertices());
    }
    
    @Test 
    public void testAddVertexExists() {
        Graph<String> graph = emptyInstance();
        graph.add("One");
        assertFalse("expected not to add the duplicate label", graph.add("One"));
        assertEquals("expected graph not to be modified", graph.add("One"), graph);
    }
    
    /* Test methods for remove(L vertex)  * * * * * * * * * * * * * * * * * */
    @Test
    public void testRemoveVertexDNE() {
        Graph<String> graph = emptyInstance();
        assertFalse("expected false, graph does not contain the vertex", graph.remove("New"));
        assertEquals("expected graph to not be modified", 
                graph, graph.remove("New"));
    }
    
    @Test 
    public void testRemoveVertexExists() {
        Graph<String> graph = emptyInstance();
        graph.add("One");
        assertTrue("expected to successfully remove vertex", graph.remove("One"));
        assertEquals("expected graph to now be empty", Collections.emptySet(), graph.vertices());
    }
    
    /* Test methods for set(L source, L target, int weight) * * * * * * * * */
    
    // Setting an edge b/w existing nodes --> This tests both when there is no edge & when an edge exists
    @Test
    public void testSetEdgeExistingNodes() {
        Graph<String> graph = emptyInstance();
        graph.add("One");
        graph.add("Two");
        assertEquals("expected return 0", 0, graph.set("One", "Two", 2));
        assertEquals("expected edge now exists", 2, graph.set("one", "Two", 1));
    }
    
    // Setting an edge where at least one vertex does not yet exist
    @Test
    public void testSetMissingVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("One");
        assertEquals("expected return 0", graph.set("One", "Two", 2));
        assertEquals("expected edge now exists", 2, graph.set("One", "Two", 1));
        assertEquals("expected vertex 'Two' now exists", 
                new HashSet<String>(Arrays.asList("One", "Two")), graph.vertices());
    }
    
    // Removing an edge (weight = 0)
    @Test
    public void testSetRemoveEdge() {
        Graph<String> graph = emptyInstance();
        graph.set("One", "Two", 2);
        assertEquals("expected old edge weight returned", 2, graph.set("One", "Two", 0));
        assertEquals("expected no edges left", 0, graph.set("One", "Two", 2));
    }
    
    // Attempting to remove an edge that DNE
    @Test
    public void testSetRemoveMissingEdge() {
        Graph<String> graph = emptyInstance();
        assertEquals("expect return 0", 0, graph.set("One", "Two", 0));
        assertEquals("expect no modification", graph, graph.set("One", "Two", 0));
    }
    
    /* Test methods for sources(L target) * * * * * * * * * * * * * * * * * */
    @Test
    public void testSourcesWithSources() {
        Graph<String> graph = emptyInstance();
        graph.set("Two", "One", 2);
        graph.set("Three", "One", 3);
        Map<String, Integer> expectedMap = new HashMap<String, Integer>() {{
            put("Two", 2);
            put("Three", 3);
        }};
        assertEquals("expect map of sources to include 'Two' and 'Three'", expectedMap, graph.sources("One"));
    }
    
    @Test
    public void testSourcesWithNoSources() {
        Graph<String> graph = emptyInstance();
        graph.set("One", "Two", 1);
        assertTrue("expect empty map", graph.sources("One").isEmpty());
    }
    
    /* Test methods for targets(L source) * * * * * * * * * * * * * * * * * */
    @Test
    public void testTargetsWithTargets() {
        Graph<String> graph = emptyInstance();
        graph.set("One", "Two", 2);
        graph.set("One", "Three", 3);
        Map<String, Integer> expectedMap = new HashMap<String, Integer>() {{
            put("Two", 2);
            put("Three", 3);
        }};
        assertEquals("expect map of targets to include 'Two' and 'Three'", expectedMap, graph.targets("One"));
    }
    
    @Test
    public void testTargetsWithNoTargets() {
        Graph<String> graph = emptyInstance();
        graph.set("Two", "One", 1);
        assertTrue("expect empty map", graph.targets("One").isEmpty());
    }
    
    /* Test methods for vertices()  * * * * * * * * * * * * * * * * * * * * */
    @Test 
    public void testVerticesNoVertices() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected empty vertex set", graph.vertices().isEmpty());
    }
    
    @Test
    public void testVerticesWithVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("One");
        graph.add("Two");
        graph.add("Three");
        assertEquals("expect set of 3 vertices", 
                new HashSet<String>(Arrays.asList("One", "Two", "Three")), graph.vertices());
    }
}
