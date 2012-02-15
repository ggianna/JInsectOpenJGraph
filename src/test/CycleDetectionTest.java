package test;
import salvo.jesus.graph.*;
import salvo.jesus.graph.algorithm.*;
import junit.framework.*;
import java.util.*;

/**
 * Test case for CycleDetectionAlgorithm.
 *
 * @author John V. Sichi
 */
public class CycleDetectionTest extends TestCase
{
    private DirectedGraph graph;
    private Vertex v1,v2,v3,v4,v5,v6;
    private Edge e12,e23,e31,e45,e54,e34,e56;
    private CycleDetectionAlgorithm cycleDetector;

    public CycleDetectionTest(String name)
    {
        super(name);
    }

    public void setUp()
        throws Exception
    {
        graph = new DirectedGraphImpl();
        v1 = new VertexImpl();
        v2 = new VertexImpl();
        v3 = new VertexImpl();
        v4 = new VertexImpl();
        v5 = new VertexImpl();
        v6 = new VertexImpl();
        e12 = graph.addEdge(v1,v2);
        e23 = graph.addEdge(v2,v3);
        e31 = graph.addEdge(v3,v1);
        e45 = graph.addEdge(v4,v5);
        e54 = graph.addEdge(v5,v4);
        e34 = graph.addEdge(v3,v4);
        e56 = graph.addEdge(v5,v6);
        cycleDetector = new CycleDetectionAlgorithmDFS(graph);
    }

    public void testFindCycleSubgraph()
        throws Exception
    {
        DirectedGraph subgraph = new DirectedGraphImpl();
        cycleDetector.findCycleSubgraph(subgraph);
        Set vertexSet = subgraph.getVertexSet();
        Set edgeSet = subgraph.getEdgeSet();
        Assert.assertEquals(true,vertexSet.contains(v1));
        Assert.assertEquals(true,vertexSet.contains(v2));
        Assert.assertEquals(true,vertexSet.contains(v3));
        Assert.assertEquals(true,vertexSet.contains(v4));
        Assert.assertEquals(true,vertexSet.contains(v5));
        Assert.assertEquals(false,vertexSet.contains(v6));
        Assert.assertEquals(true,edgeSet.contains(e12));
        Assert.assertEquals(true,edgeSet.contains(e23));
        Assert.assertEquals(true,edgeSet.contains(e31));
        Assert.assertEquals(true,edgeSet.contains(e45));
        Assert.assertEquals(true,edgeSet.contains(e54));
        Assert.assertEquals(false,edgeSet.contains(e34));
        Assert.assertEquals(false,edgeSet.contains(e56));
    }

    public void testFindCycleSubgraphVertex()
        throws Exception
    {
        DirectedGraph subgraph = new DirectedGraphImpl();
        cycleDetector.findCycleSubgraph(subgraph,v4);
        Set vertexSet = subgraph.getVertexSet();
        Set edgeSet = subgraph.getEdgeSet();
        Assert.assertEquals(true,vertexSet.contains(v4));
        Assert.assertEquals(true,vertexSet.contains(v5));
        Assert.assertEquals(false,vertexSet.contains(v1));
        Assert.assertEquals(false,vertexSet.contains(v6));
        Assert.assertEquals(true,edgeSet.contains(e45));
        Assert.assertEquals(true,edgeSet.contains(e54));
        Assert.assertEquals(false,edgeSet.contains(e12));
        Assert.assertEquals(false,edgeSet.contains(e34));
        Assert.assertEquals(false,edgeSet.contains(e56));
    }

    public void testFindCycleSubgraphEdge()
        throws Exception
    {
        DirectedGraph subgraph = new DirectedGraphImpl();
        cycleDetector.findCycleSubgraph(subgraph,e34);
        Set vertexSet = subgraph.getVertexSet();
        Set edgeSet = subgraph.getEdgeSet();
        Assert.assertEquals(0,vertexSet.size());
        Assert.assertEquals(0,edgeSet.size());
    }

    public void testDetectCycles()
        throws Exception
    {
        Assert.assertEquals(true,cycleDetector.detectCycles());
        Assert.assertEquals(true,cycleDetector.detectCycles(v1));
        Assert.assertEquals(false,cycleDetector.detectCycles(v6));
        Assert.assertEquals(true,cycleDetector.detectCycles(e12));
        Assert.assertEquals(true,cycleDetector.detectCycles(e45));
        Assert.assertEquals(true,cycleDetector.detectCycles(e45));
        Assert.assertEquals(false,cycleDetector.detectCycles(e34));
        Assert.assertEquals(false,cycleDetector.detectCycles(e56));
    }

    public static Test suite() {
        return new TestSuite( CycleDetectionTest.class );
    }

}

// End CycleDetectionTest.java
