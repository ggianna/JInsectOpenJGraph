package test;
import salvo.jesus.graph.*;
import salvo.jesus.graph.listener.*;
import junit.framework.*;

/**
 * Test case for ImmutableGraphListener.
 *
 * @author John V. Sichi
 */
public class ImmutableGraphListenerTest extends TestCase
{
    public ImmutableGraphListenerTest(String name)
    {
        super(name);
    }

    public void testImmutability()
        throws Exception
    {
        Graph g = new GraphImpl();
        Vertex v1 = new VertexImpl( "1" );
        Vertex v2 = new VertexImpl( "2" );
        Vertex v3 = new VertexImpl( "3" );
        Vertex v4 = new VertexImpl( "4" );
        Edge e12 = new EdgeImpl( v1, v2 );

        new ImmutableGraphListener(g);
        try {
            g.add(v4);
            Assert.fail("immutability exception expected");
        } catch (Throwable ex) {
            // we expect an immutability exception here
        }
        try {
            g.remove(v1);
            Assert.fail("immutability exception expected");
        } catch (Throwable ex) {
            // we expect an immutability exception here
        }
        try {
            g.addEdge(v1,v3);
            Assert.fail("immutability exception expected");
        } catch (Throwable ex) {
            // we expect an immutability exception here
        }
        try {
            g.removeEdge(e12);
            Assert.fail("immutability exception expected");
        } catch (Throwable ex) {
            // we expect an immutability exception here
        }
    }

    public static Test suite() {
        return new TestSuite( ImmutableGraphListenerTest.class );
    }


}

// End ImmutableGraphListenerTest.java
