package test;
import salvo.jesus.graph.*;
import junit.framework.*;

/**
 * Some test cases illustrating openjgraph bugs/inconsistencies.
 *
 * @author John V. Sichi
 */
public class BugTest extends TestCase
{
    public BugTest(String name)
    {
        super(name);
    }

    /**
     * This test shows a bug with vertices having neither a string nor an
     * associated object.  When an edge is constructed on such a vertex, it
     * calls Vertex.toString(), which results in a NullPointerException.
     */
    public void testAnonymousVertex()
    {
        Vertex v1 = new VertexImpl();
        Vertex v2 = new VertexImpl();
        Edge e = new EdgeImpl(v1,v2);
    }

    /**
     * This test illustrates that when a string is set, everything is fine.
     */
    public void testNamedVertex()
    {
        Vertex v1 = new VertexImpl("a");
        Vertex v2 = new VertexImpl("b");
        Edge e = new EdgeImpl(v1,v2);
    }

    /**
     * This test illustrates a problem with adding an edge to a DAG when the
     * one or both of the incident vertices have not yet been added to the
     * DAG.  The DAG attempts to call isPath to verify that no cycle is being
     * created, resulting in an IndexOutOfBoundsException.  This is bug#474597
     * on SourceForge.
     */
    public void testMissingVertexDAG()
        throws Exception
    {
        DirectedAcyclicGraph g = new DirectedAcyclicGraphImpl();
        Vertex v1 = new VertexImpl("a");
        Vertex v2 = new VertexImpl("b");
        DirectedEdge e = new DirectedEdgeImpl(v1,v2);
        g.addEdge(e);
    }

    /**
     * This test illustrates that the above problem doesn't exist for the
     * base DirectedGraphImpl.
     */
    public void testMissingVertex()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex v1 = new VertexImpl("a");
        Vertex v2 = new VertexImpl("b");
        DirectedEdge e = new DirectedEdgeImpl(v1,v2);
        g.addEdge(e);
    }

    /**
     * This test illustrates that it is possible to add the same vertex to the
     * same graph twice, and the graph doesn't realize that it's the same.
     */
    public void testDuplicateVertex()
        throws Exception
    {
        Graph g = new GraphImpl();
        Vertex v = new VertexImpl("a");
        g.add(v);
        g.add(v);
        Assert.assertEquals(1,g.getVerticesCount());
    }

    /**
     * This test illustrates that it is possible to add the same edge to the
     * same graph twice, and the graph doesn't realize that it's the same.
     */
    public void testDuplicateEdge()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex v1 = new VertexImpl("a");
        Vertex v2 = new VertexImpl("b");
        DirectedEdge e = new DirectedEdgeImpl(v1,v2);
        g.addEdge(e);
        g.addEdge(e);
        Assert.assertEquals(1,g.getOutgoingEdges(v1).size());
    }

    /**
     * This test illustrates a problem in the implementation of isPath.  The
     * second invocation of isPath returns true when it should return false.
     * The problem arises because isPath passes a StopAtVisitor to the
     * DFSTraversal, with the result that after the first invocation of isPath,
     * the state of the DFSTraversal is not cleared (vertex c is left sitting
     * on the stack).  This causes the second invocation of isPath to return
     * the wrong answer.  To fix this, it is important for all traversals to
     * reinitialize themselves no matter which exit path is taken.
     */
    public void testGraphTraversalReuse()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex a = new VertexImpl("a");
        Vertex b = new VertexImpl("b");
        Vertex c = new VertexImpl("c");
        g.add(a);
        g.add(b);
        g.add(c);
        g.addEdge(a,c);
        g.addEdge(a,b);
        g.addEdge(c,b);
        Assert.assertEquals(true,g.isPath(a,b));
        Assert.assertEquals(false,g.isPath(b,c));
    }

    /**
     * This test illustrates a problem in the implementation of SimplePathImpl.
     * The problem is that when an edge is added explicitly, it gets added
     * twice.  This occurs because SimplePathImpl.addEdge calls super.addEdge,
     * which adds the edge but also adds the vertices which aren't part of the
     * path yet, and PathImpl.add(Vertex) adds ANOTHER edge because it doesn't
     * realize that this has already been done.
     */
    public void testSimplePathDuplicateEdges()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex a = new VertexImpl("a");
        Vertex b = new VertexImpl("b");
        Vertex c = new VertexImpl("c");
        g.add(a);
        g.add(b);
        g.add(c);
        Edge ab = g.addEdge(a,b);
        Edge bc = g.addEdge(b,c);
        Edge ac = g.addEdge(a,c);
        SimplePath path = new SimplePathImpl();
        path.add(a);
        path.addEdge(ab);
        path.addEdge(bc);
        Assert.assertEquals(3,path.getVerticesCount());
        Assert.assertEquals(1,path.getEdges(a).size());
        Assert.assertEquals(2,path.getEdges(b).size());
        Assert.assertEquals(1,path.getEdges(c).size());
    }

    /**
     * This test illustrates a workaround for the previous problem.  However,
     * this workaround means that you can't reuse the edges from the original
     * graph, which is a problem if those edges bear important information.
     */
    public void testSimplePathWorkaround()
        throws Exception
    {
        DirectedGraph g = new DirectedGraphImpl();
        Vertex a = new VertexImpl("a");
        Vertex b = new VertexImpl("b");
        Vertex c = new VertexImpl("c");
        g.add(a);
        g.add(b);
        g.add(c);
        Edge ab = g.addEdge(a,b);
        Edge bc = g.addEdge(b,c);
        Edge ac = g.addEdge(a,c);
        SimplePath path = new SimplePathImpl();
        path.add(a);
        path.add(b);
        path.add(c);
        Assert.assertEquals(3,path.getVerticesCount());
        Assert.assertEquals(1,path.getEdges(a).size());
        Assert.assertEquals(2,path.getEdges(b).size());
        Assert.assertEquals(1,path.getEdges(c).size());
    }

    public static Test suite() {
        return new TestSuite( BugTest.class );
    }
}

// End BugTest.java
