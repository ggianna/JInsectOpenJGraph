package examples;

import salvo.jesus.graph.*;
import salvo.jesus.graph.visual.*;
import salvo.jesus.graph.visual.layout.*;
import salvo.jesus.graph.visual.drawing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * An example of a radial tree layout. Graph similar to the graph shown on
 * page 53, Graph Drawing - Algorithms for the Visualization of Graphs.
 *
 * @author  Jesus M. Salvo Jr.
 */

public class SampleRadialTreeLayout extends JFrame {
    VisualGraph         vgraph;

    public SampleRadialTreeLayout() {}

    private Tree initTree() throws Exception {
        Tree    tree = new TreeImpl();
        Vertex  v1, v2, v3, v4, v5, v6, v7, v8, v9;
        Vertex  v10, v11, v12, v13;
        Vertex  v14, v15, v16, v17, v18, v19;
        Vertex  v20, v21, v22, v23, v24, v25, v26;
        Vertex  v27, v28, v29, v30, v31, v32, v33, v34, v35, v36;

        v1 = new VertexImpl( "1" );
        v2 = new VertexImpl( "2" );
        v3 = new VertexImpl( "3" );
        v4 = new VertexImpl( "4" );

        v5 = new VertexImpl( "5" );
        v6 = new VertexImpl( "6" );
        v7 = new VertexImpl( "7" );
        v8 = new VertexImpl( "8" );
        v9 = new VertexImpl( "9" );
        v10 = new VertexImpl( "10" );
        v11 = new VertexImpl( "11" );
        v12 = new VertexImpl( "12" );
        v13 = new VertexImpl( "13" );

        v14 = new VertexImpl( "14" );
        v15 = new VertexImpl( "15" );
        v16 = new VertexImpl( "16" );
        v17 = new VertexImpl( "17" );
        v18 = new VertexImpl( "18" );
        v19 = new VertexImpl( "19" );

        v20 = new VertexImpl( "20" );
        v21 = new VertexImpl( "21" );
        v22 = new VertexImpl( "22" );
        v23 = new VertexImpl( "23" );
        v24 = new VertexImpl( "24" );
        v25 = new VertexImpl( "25" );
        v26 = new VertexImpl( "26" );

        v27 = new VertexImpl( "27" );
        v28 = new VertexImpl( "28" );
        v29 = new VertexImpl( "29" );
        v30 = new VertexImpl( "30" );
        v31 = new VertexImpl( "31" );
        v32 = new VertexImpl( "32" );
        v33 = new VertexImpl( "33" );
        v34 = new VertexImpl( "34" );
        v35 = new VertexImpl( "35" );
        v36 = new VertexImpl( "36" );

        tree.addNode( null, v1 );

        tree.addNode( v1, v2 );
        tree.addNode( v2, v3 );
        tree.addNode( v2, v4 );

        tree.addNode( v3, v5 );
        tree.addNode( v3, v6 );

        tree.addNode( v4, v7 );
        tree.addNode( v4, v8 );

        tree.addNode( v5, v9 );

        tree.addNode( v6, v10 );
        tree.addNode( v6, v11 );

        tree.addNode( v8, v12 );
        tree.addNode( v8, v13 );


        tree.addNode( v1, v14 );

        tree.addNode( v14, v15 );
        tree.addNode( v14, v16 );
        tree.addNode( v14, v17 );
        tree.addNode( v14, v18 );
        tree.addNode( v14, v19 );

        tree.addNode( v15, v20 );
        tree.addNode( v20, v21 );

        tree.addNode( v16, v22 );
        tree.addNode( v16, v23 );

        tree.addNode( v19, v24 );
        tree.addNode( v19, v25 );
        tree.addNode( v25, v26 );


        tree.addNode( v1, v27 );

        tree.addNode( v27, v28 );
        tree.addNode( v27, v29 );
        tree.addNode( v27, v30 );

        tree.addNode( v28, v31 );
        tree.addNode( v31, v32 );
        tree.addNode( v31, v33 );

        tree.addNode( v30, v34 );
        tree.addNode( v30, v35 );
        tree.addNode( v35, v36 );

        /*
        tree.addNode( v11, v15 );
        tree.addNode( v12, v16 );
        tree.addNode( v13, v17 );
        */


        return tree;
    }

    private void showTree( Tree tree ) {
        GraphEditor         editor;
        GraphLayoutManager  layoutManager;

        // Get a VisualGraph
        editor = new GraphEditor();
        editor.setGraph( tree );
        vgraph = editor.getVisualGraph();

        // Initialise a Tree layout manager
        //layoutManager = new LayeredTreeLayout( editor.getVisualGraph() );
        layoutManager = new RadialTreeLayout( editor.getVisualGraph() );
        editor.setGraphLayoutManager( layoutManager );

        // Make it all visible
        this.getContentPane().setLayout( new GridLayout(1,2));
        this.getContentPane().add( editor );

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension( screenSize.width - 80, screenSize.height - 80 );

        this.setSize( frameSize );
        this.setLocation((int)(screenSize.getWidth() - frameSize.getWidth()) / 2, (int)(screenSize.getHeight() - frameSize.getHeight()) / 2);

        // Terminate the application when the window closes
        this.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) { System.exit(0); }
          });
    }

    public static void main( String args[] ) throws Exception {
        SampleRadialTreeLayout    app = new SampleRadialTreeLayout();
        Tree tree = app.initTree();
        app.showTree( tree );
        app.setTitle( "SampleTreeLayout" );
        app.setVisible( true );
        app.vgraph.layout();
    }
}