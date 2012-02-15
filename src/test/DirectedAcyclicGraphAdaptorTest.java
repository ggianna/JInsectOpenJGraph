package test;
import salvo.jesus.graph.*;
import salvo.jesus.graph.adaptor.*;
import junit.framework.*;
import java.util.*;

/**
 * Test case for DirectedAcyclicGraphAdaptor.
 *
 * @author John V. Sichi
 */
public class DirectedAcyclicGraphAdaptorTest extends TestCase
{
    public DirectedAcyclicGraphAdaptorTest(String name)
    {
        super(name);
    }

    public void testNoExistingCycle()
        throws Exception
    {
        Vertex v1 = new VertexImpl("v1");
        Vertex v2 = new VertexImpl("v2");
        Vertex v3 = new VertexImpl("v3");
        DirectedGraph g = new DirectedGraphImpl();
        g.add(v1);
        g.add(v2);
        g.addEdge(v1,v2);
        g.addEdge(v2,v3);
        DirectedAcyclicGraphAdaptor dag = new DirectedAcyclicGraphAdaptor(g);
        Iterator topoIter = dag.topologicalSort().iterator();
        Assert.assertSame(v1,topoIter.next());
        Assert.assertSame(v2,topoIter.next());
        Assert.assertSame(v3,topoIter.next());

        // make sure that cycles are caught when g is modified
        try {
            g.addEdge(v3,v1);
            Assert.fail("CycleException expected");
        } catch (CycleException ex) {
            // expected
        }

        // verify that once dag is destroyed, g can have cycles again
        dag.destroy();
        g.addEdge(v3,v1);
    }

    public void testExistingCycle()
        throws Exception
    {
        Vertex v1 = new VertexImpl("v1");
        Vertex v2 = new VertexImpl("v2");
        Vertex v3 = new VertexImpl("v3");
        DirectedGraph g = new DirectedGraphImpl();
        g.add(v1);
        g.add(v2);
        g.addEdge(v1,v2);
        g.addEdge(v2,v3);
        g.addEdge(v3,v1);
        try {
            DirectedAcyclicGraphAdaptor dag =
                new DirectedAcyclicGraphAdaptor(g);
            Assert.fail("CycleException expected");
        } catch (CycleException ex) {
            // we expect a CycleException here
        }
    }

    public static Test suite() {
        return new TestSuite( DirectedAcyclicGraphAdaptorTest.class );
    }

}

// End DirectedAcyclicGraphAdaptorTest.java
