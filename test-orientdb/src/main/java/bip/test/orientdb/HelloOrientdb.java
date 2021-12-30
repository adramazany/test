package bip.test.orientdb;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * Created by ramezani on 6/14/2018.
 *
 * https://orientdb.com/docs/2.0/orientdb.wiki/Tutorial-Java.html
 */
public class HelloOrientdb {

    static TransactionalGraph graph;

    public static void main(String[] args) {
        System.out.println("HelloOrientdb starting ... ");
        // 1
        //graph = new OrientGraph("plocal:sepehr", "root", "orientdb@bip43");
        //2
        //graph = new OrientGraph("remote:localhost/sepehr", "root", "orientdb@bip43");
        graph = new OrientGraph("remote:192.168.88.2/GratefulDeadConcerts", "root", "orientdb@bip43");
        //3
        //OGraphDatabase odb = new OGraphDatabase("local:test").create();
        //TransactionalGraph graph = new OrientGraph(odb);
        addVertexEdge();
        graph.commit();
        //((OrientGraph)graph).close


        System.out.println("HelloOrientdb end.");
    }
    static void createTypes(){
/*
        odb.setUseCustomTypes(true);
        odb.createVertexType("Person");
        odb.createVertexType("Address");
*/
    }

    static void addVertexEdge(){
        Vertex vPerson = graph.addVertex("class:Person");
        vPerson.setProperty("firstName", "John");
        vPerson.setProperty("lastName", "Smith");

        Vertex vAddress = graph.addVertex("class:Address");
        vAddress.setProperty("street", "Van Ness Ave.");
        vAddress.setProperty("city", "San Francisco");
        vAddress.setProperty("state", "California");

        //Edge eLives = graph.addEdge(null, vPerson, vAddress, "lives");
        Edge eLives2 = graph.addEdge("class:Live", vPerson, vAddress, "lives");
    }



}
