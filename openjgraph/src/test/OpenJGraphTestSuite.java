package test;

import junit.framework.*;

/**
 * @author Jesus M. Salvo Jr.
 *
 * $Id: OpenJGraphTestSuite.java,v 1.2 2002/08/24 12:49:42 jmsalvo Exp $
 */

public class OpenJGraphTestSuite extends TestCase {

  public OpenJGraphTestSuite( String name) {
    super( name );
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();

    suite.addTest( BugTest.suite() );
    suite.addTest( CycleDetectionTest.suite() );
    suite.addTest( DirectedAcyclicGraphAdaptorTest.suite() );
    suite.addTest( GraphContractionTest.suite() );
    suite.addTest( ImmutableGraphListenerTest.suite() );
    suite.addTest( TreeImplTest.suite() );

    return suite;
  }
}