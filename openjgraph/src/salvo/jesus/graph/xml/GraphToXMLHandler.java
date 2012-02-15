package salvo.jesus.graph.xml;

import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.visual.VisualGraph;

/**
 * An interface to handle events generated by <tt>GraphToXMLEventGenerator</tt>.
 * Ideally, a <tt>GraphToXMLEventGenerator</tt> serializes all Vertices, before serializes
 * all Edges.
 *
 * @author Jesus M. Salvo Jr.
 */

public interface GraphToXMLHandler {

    /**
     * Called only once before the serialization process begins.
     */
    public void startSerialize( Graph graph ) throws Exception;

    /**
     * Called only once before the serialization process begins.
     */
    public void startSerialize( VisualGraph vGraph ) throws Exception;

    /**
     * Called only once for each <tt>Vertex</tt> in the <tt>Graph</tt> being serialized.
     *
     * @param   vertex  The <tt>Vertex</tt> that needs to be serialized
     */
    public void serializeVertex( Vertex vertex ) throws Exception;

    public void endSerializeVertex( Vertex vertex ) throws Exception;

    /**
     * Called only once for each <tt>Edge</tt> in the <tt>Graph</tt> being serialized.
     *
     * @param   edge  The <tt>Edge</tt> that needs to be serialized
     */
    public void serializeEdge( Edge edge ) throws Exception;

    public void endSerializeEdge( Edge edge ) throws Exception;

    /**
     * Called only once after the serialization process begins.
     */
    public void endSerialize() throws Exception;

    /**
     * Returns the <tt>GraphToXMLEventGenerator</tt> that is generating the events.
     */
    public GraphToXMLEventGenerator getEventGenerator();

}