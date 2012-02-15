package test;
import salvo.jesus.graph.*;
import salvo.jesus.graph.algorithm.*;
import junit.framework.*;
import java.util.*;

/**
 * Test case for GraphContractionAlgorithm
 *
 * @author John V. Sichi
 * @version $Id: GraphContractionTest.java,v 1.2 2002/08/23 11:57:49 jmsalvo Exp $
 */
public class GraphContractionTest extends TestCase
{
    public GraphContractionTest(String name)
    {
        super(name);
    }

    private static class SimpleGraphContraction
        extends GraphContractionAlgorithm
    {
        SimpleGraphContraction(Graph g)
        {
            super(g);
        }

        public boolean shouldBeSelfLoop(Edge e)
        {
            return true;
        }

        public Edge copyEdge(Edge e,Vertex vA,Vertex vB)
            throws Exception
        {
            Edge newEdge = new DirectedEdgeImpl(vA,vB);
            getGraph().addEdge(newEdge);
            return newEdge;
        }
    }

    public void testSimpleContraction()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex v1 = new VertexImpl("v1");
        Vertex v2 = new VertexImpl("v2");
        Vertex v3 = new VertexImpl("v3");
        g.add(v1);
        g.add(v2);
        g.add(v3);
        g.addEdge(v1,v3);
        GraphContractionAlgorithm contraction = new SimpleGraphContraction(g);
        contraction.contractVertexPair(v2,v3);
        Assert.assertEquals(2,g.getVerticesCount());
        Assert.assertNotNull(g.getEdge(v1,v2));
        Assert.assertSame(v1,contraction.getContractionVertex(v1));
        Assert.assertSame(v2,contraction.getContractionVertex(v2));
        Assert.assertSame(v2,contraction.getContractionVertex(v3));
    }

    public void testContractionSelfLoopCreation()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex v1 = new VertexImpl("v1");
        Vertex v2 = new VertexImpl("v2");
        Vertex v3 = new VertexImpl("v3");
        g.add(v1);
        g.add(v2);
        g.add(v3);
        g.addEdge(v1,v2);
        g.addEdge(v2,v3);
        GraphContractionAlgorithm contraction = new SimpleGraphContraction(g);
        contraction.contractVertexPair(v2,v3);
        Assert.assertEquals(2,g.getVerticesCount());
        Assert.assertNotNull(g.getEdge(v2,v2));
    }

    public void testContractionSelfLoopTransfer()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex v1 = new VertexImpl("v1");
        Vertex v2 = new VertexImpl("v2");
        g.add(v1);
        g.add(v2);
        g.addEdge(v2,v2);
        GraphContractionAlgorithm contraction = new SimpleGraphContraction(g);
        contraction.contractVertexPair(v1,v2);
        Assert.assertEquals(1,g.getVerticesCount());
        Assert.assertNotNull(g.getEdge(v1,v1));
    }

    public void testCollectionContraction()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex v1 = new VertexImpl("v1");
        Vertex v2 = new VertexImpl("v2");
        Vertex v3 = new VertexImpl("v3");
        Vertex v4 = new VertexImpl("v4");
        g.add(v1);
        g.add(v2);
        g.add(v3);
        g.add(v4);
        g.addEdge(v1,v4);
        g.addEdge(v2,v3);
        GraphContractionAlgorithm contraction = new SimpleGraphContraction(g);
        List list = new ArrayList();
        list.add(v1);
        list.add(v2);
        list.add(v3);
        contraction.contractVertices(list);
        Assert.assertEquals(2,g.getVerticesCount());
        Assert.assertNotNull(g.getEdge(v1,v4));
        Assert.assertNotNull(g.getEdge(v1,v1));
        Assert.assertSame(v1,contraction.getContractionVertex(v1));
        Assert.assertSame(v1,contraction.getContractionVertex(v2));
        Assert.assertSame(v1,contraction.getContractionVertex(v3));
        Assert.assertSame(v4,contraction.getContractionVertex(v4));
    }

    public static Test suite() {
        return new TestSuite( GraphContractionTest.class );
    }

}

// End GraphContractionTest.java
