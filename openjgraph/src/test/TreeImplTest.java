package test;

import salvo.jesus.graph.*;
import junit.framework.*;

/**
 * @author Jesus M. Salvo Jr.
 * @version $Id: TreeImplTest.java,v 1.7 2002/09/11 13:58:36 jmsalvo Exp $
 */

public class TreeImplTest extends TestCase {

  public TreeImplTest(String name) {
    super(name);
  }

  public void testEmptyTree() {
    Tree tree = new TreeImpl();

    Assert.assertEquals( null, tree.getRoot() );
    Assert.assertEquals( 0, tree.getDegree() );
    Assert.assertEquals( 0, tree.getEdgesCount());
    Assert.assertEquals( 0, tree.getHeight() );
    Assert.assertEquals( 0, tree.getLeaves().size() );
  }

  public void testSingleNodeTree() throws Exception {
    Tree tree = new TreeImpl();
    Vertex root = new VertexImpl( "root" );

    tree.addNode( null, root );

    Assert.assertEquals( root, tree.getRoot() );
    Assert.assertEquals( 0, tree.getDegree() );
    Assert.assertEquals( 0, tree.getEdgesCount() );
    Assert.assertEquals( 1, tree.getHeight());
    Assert.assertEquals( 1, tree.getLeaves().size());
    Assert.assertEquals( root, tree.getLeaves().get( 0 ));
    Assert.assertEquals( null, tree.getParent( root ) );
    Assert.assertEquals( 0, tree.getChildren( root ).size() );

    // Must not be able to change the root via addNode
    try {
      tree.addNode( null, root );
      Assert.fail( "Must not be able to modify the root of a tree, after it has been set, via addNode()" );
    }
    catch( GraphException ex ) {}

    // Make sure we cannot make a self-loop
    try {
      tree.addNode( root, root );
      Assert.fail( "Must not be able to create a self-loop in a tree." );
    }
    catch( CycleException ex ) {}

    Assert.assertEquals( root, tree.getSubTree( root ).getRoot()  );

    tree.remove( root );
    Assert.assertEquals( null, tree.getRoot() );
    Assert.assertEquals( 0, tree.getDegree() );
    Assert.assertEquals( 0, tree.getEdgesCount());
    Assert.assertEquals( 0, tree.getHeight() );
    Assert.assertEquals( 0, tree.getLeaves().size() );
  }

  public void testTwoNodeTree() throws Exception {
    Tree tree = new TreeImpl();
    Vertex v1 = new VertexImpl("v1");
    Vertex v2 = new VertexImpl("v2");

    tree.addNode( null, v1 );

    // Must not be able to modify the root via addNode()
    try {
      tree.addNode( null, v2 );
      Assert.fail( "Must not be able to modify the root of a tree, after it has been set, via addNode()" );
    }
    catch( GraphException ex ) {}

    tree.addNode( v1, v2 );

    Assert.assertEquals( v1, tree.getRoot() );
    Assert.assertEquals( 1, tree.getDegree() );
    Assert.assertEquals( 1, tree.getEdgesCount() );
    Assert.assertEquals( 2, tree.getHeight());
    Assert.assertEquals( 1, tree.getLeaves().size() );
    Assert.assertEquals( null, tree.getParent( v1 ) );
    Assert.assertEquals( v1, tree.getParent( v2 ) );
    Assert.assertEquals( 1, tree.getChildren( v1 ).size());
    Assert.assertEquals( v2, tree.getChildren( v1 ).get( 0 ) );
    Assert.assertEquals( 0, tree.getChildren( v2 ).size() );
    Assert.assertEquals( true, tree.isLeaf( v2 ));
    Assert.assertEquals( false, tree.isLeaf( v1 ));

    // Now test switching of root of the free tree
    tree.setRoot( v2 );

    Assert.assertEquals( v2, tree.getRoot() );
    Assert.assertEquals( 1, tree.getDegree() );
    Assert.assertEquals( 1, tree.getEdgesCount() );
    Assert.assertEquals( 2, tree.getHeight());
    Assert.assertEquals( 1, tree.getLeaves().size() );
    Assert.assertEquals( v2, tree.getParent( v1 ) );
    Assert.assertEquals( null, tree.getParent( v2 ) );
    Assert.assertEquals( 1, tree.getChildren( v2 ).size());
    Assert.assertEquals( v1, tree.getChildren( v2 ).get( 0 ) );
    Assert.assertEquals( 0, tree.getChildren( v1 ).size() );
    Assert.assertEquals( true, tree.isLeaf( v1 ));
    Assert.assertEquals( false, tree.isLeaf( v2 ));

    // Null root must never be allowed if there are nodes
    try {
      tree.setRoot( null );
      Assert.fail( "Must not be able to set a null root of there are nodes in the tree");
    }
    catch( GraphException ex ) {}

    // Now make sure we cannot create a cycle
    try {
      tree.addEdge( v2, v1 );
      Assert.fail( "Must not be able to create a cycle in a tree");
    }
    catch( CycleException ex ) {}
    try {
      tree.addEdge( v1, v2 );
      Assert.fail( "Must not be able to create a cycle in a tree");
    }
    catch( CycleException ex ) {}

    // Must not be able to remove the root of the tree if the root has child nodes.
    Vertex root = tree.getRoot();
    try {
      tree.remove( root );
      Assert.fail( "Must not be able to remove the root (" + root + ") from tree because it still has child nodes");
    }
    catch( IllegalTreeException ex ) {}

    Assert.assertEquals( v2, tree.getSubTree( v2 ).getRoot()  );
    Assert.assertEquals( 2, tree.getSubTree( v2 ).getVerticesCount() );
    Assert.assertEquals( v1, tree.getSubTree( v1 ).getRoot()  );
    Assert.assertEquals( 1, tree.getSubTree( v1 ).getVerticesCount() );

    // Must be OK to remove leaf
    tree.remove( v1 );

    Assert.assertEquals( v2, tree.getRoot() );
    Assert.assertEquals( 0, tree.getDegree() );
    Assert.assertEquals( 0, tree.getEdgesCount() );
    Assert.assertEquals( 1, tree.getHeight());
    Assert.assertEquals( 1, tree.getLeaves().size());
    Assert.assertEquals( v2, tree.getLeaves().get( 0 ));
    Assert.assertEquals( null, tree.getParent( v2 ) );
    Assert.assertEquals( 0, tree.getChildren( v2 ).size() );

  }

  public void testMutliNodeTree() throws Exception {
    Tree tree = new TreeImpl();
    Vertex root = new VertexImpl( "Root" );
    Vertex v1 = new VertexImpl("L1A");
    Vertex v2 = new VertexImpl("L1B");
    Vertex v3 = new VertexImpl("L2A");

    tree.addNode( null, root );
    tree.addNode( root, v1 );
    tree.addNode( root, v2 );
    tree.addNode( v1, v3 );

    Assert.assertEquals( root, tree.getRoot() );
    Assert.assertEquals( 2, tree.getDegree() );
    Assert.assertEquals( 3, tree.getEdgesCount() );
    Assert.assertEquals( 3, tree.getHeight());
    Assert.assertEquals( 2, tree.getLeaves().size() );

    Assert.assertEquals( 2, tree.getChildren( root ).size() );
    Assert.assertEquals( true, tree.getChildren( root ).contains( v1 ));
    Assert.assertEquals( true, tree.getChildren( root ).contains( v2 ));
    Assert.assertEquals( null, tree.getParent( root ));
    Assert.assertEquals( false, tree.isLeaf( root ));

    Assert.assertEquals( 1, tree.getChildren( v1 ).size() );
    Assert.assertEquals( true, tree.getChildren( v1 ).contains( v3 ) );
    Assert.assertEquals( root, tree.getParent( v1 ));
    Assert.assertEquals( false, tree.isLeaf( v1 ));

    Assert.assertEquals( 0, tree.getChildren( v2 ).size() );
    Assert.assertEquals( root, tree.getParent( v2 ));
    Assert.assertEquals( true, tree.isLeaf( v2 ));

    Assert.assertEquals( 0, tree.getChildren( v3 ).size() );
    Assert.assertEquals( v1, tree.getParent( v3 ));
    Assert.assertEquals( true, tree.isLeaf( v3 ));

    try {
      // Make sure that non-leaf nodes cannot be removed
      tree.remove( v1 );
      Assert.fail( "Non-leaf nodes must not be removed." );
    }
    catch( IllegalTreeException ex ) {}
  }

  public static Test suite() {
      return new TestSuite( TreeImplTest.class );
  }

}