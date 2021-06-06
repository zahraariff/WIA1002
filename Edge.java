/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alwaysontime;

/**
 *
 * @author user
 */
public class Edge <V extends Comparable <V>, E extends Comparable<E>>{
    Vertex<V,E>toVertex;
    E Stopcost;
    Edge<V,E>nextEdge;

    public Edge() {
        toVertex=null;
        Stopcost=null;
        nextEdge=null;
    }

    public Edge(Vertex<V, E> toVertex, E Stopcost, Edge<V, E> nextEdge) {
        this.toVertex = toVertex;
        this.Stopcost = Stopcost;
        this.nextEdge = nextEdge;
    }
}

