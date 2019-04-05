/*
****Program 3****

Huy Tran 818608122
    cssc0763
Alex Gutierrez 821394815
    cssc0784
*/

package edu.sdsu.cs.datastructures;

import java.util.*;

public class DirectedGraph<V> implements IGraph<V> {

    Map<V, LinkedList<V>> graph;

    public DirectedGraph() {
        graph = new LinkedHashMap<>();
    }

    @Override
    public void add(V vertexName) {
        if (graph.containsKey(vertexName)) return;
        graph.put(vertexName, new LinkedList<V>());
    }

    @Override
    public void connect(V start, V destination) {
        if (!contains(start) || !contains(destination))
            throw new NoSuchElementException("no elements");
        graph.get(start).add(destination);
    }

    @Override
    public void clear() {
        graph.clear();
    }

    @Override
    public boolean contains(V label) {
        return graph.containsKey(label);
    }

    @Override
    public void disconnect(V start, V destination) {
        if (!contains(start) || !contains(destination))
            throw new NoSuchElementException("no elements");
        if (isConnected(start, destination))
            graph.get(start).remove(destination);
    }

    @Override

    public boolean isConnected(V start, V destination) {
        if (!contains(start) || !contains(destination))
            throw new NoSuchElementException("no elements");
        if (((Comparable<V>) start).compareTo(destination) == 0)
            return true;
        if (graph.get(start).contains(destination))
            return true;
        LinkedList<V> visited = new LinkedList<>();
        LinkedList<V> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            V current = queue.poll();
            if (((Comparable<V>) current).compareTo(destination) == 0)
                return true;
            for (V neighbor : neighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    @Override
    public Iterable<V> neighbors(V vertexName){
        if (!contains(vertexName))
            throw new NoSuchElementException("no elements");
        return graph.get(vertexName);

    }

    @Override
    public void remove(V vertexName) {
        if (!contains(vertexName))
            throw new NoSuchElementException("no elements");
        for (V vertex : vertices()) {
            if (isConnected(vertexName, vertex))
                disconnect(vertexName, vertex);
            if (isConnected(vertex, vertexName))
                disconnect(vertex, vertexName);
        }
        graph.remove(vertexName);
    }

    @Override
    public List<V> shortestPath(V start, V destination) {
        if (!contains(start) || !contains(destination))
            throw new NoSuchElementException("no elements");

        LinkedList<V> path = new LinkedList<>();
        Map<V,Boolean> visited = new HashMap<>();
        Map<V,V> prev = new HashMap<>();

        Queue<V> queue = new LinkedList<>();
        V current = start;
        queue.add(current);
        visited.put(current,true);
        while(!queue.isEmpty()){
            current = queue.remove();
            if(((Comparable<V>) current).compareTo(destination) == 0){
                break;
            }
            else{
                for(V neighbor : neighbors(current)){
                    if(!visited.containsKey(neighbor)){
                        queue.add(neighbor);
                        visited.put(neighbor,true);
                        prev.put(neighbor,current);
                    }
                }
            }
        }
        if(((Comparable<V>) current).compareTo(destination) != 0)
            System.out.println("can't reach destination");
        for(V dest = destination; dest != null; dest = prev.get(dest)){
            path.add(dest);
        }
        LinkedList<V> path2 = new LinkedList<>();
        for(int i = path.size() - 1 ; i >= 0; i--){
            path2.add(path.get(i));
        }
        return path2;
    }

    @Override
    public int size() {
        return graph.size();
    }

    @Override
    public Iterable<V> vertices() {
        LinkedList<V> list = new LinkedList<>(graph.keySet());
        return list;
    }

    @Override
    public IGraph<V> connectedGraph(V origin) {
        if (!contains(origin))
            throw new NoSuchElementException("no elements");
        DirectedGraph<V> myGraph = new DirectedGraph<>();
        myGraph.add(origin);
        for (V vertex : vertices()) {
            if (isConnected(origin, vertex)) {
                myGraph.add(vertex);
                myGraph.connect(origin, vertex);
            }
        }
        return myGraph;
    }

    @Override
    public String toString(){
        String ret = "";
        int degree = 0;

        for(V vertex : vertices()){
            degree = ((Collection<V>)neighbors(vertex)).size();
            ret += vertex + "\n\tThis vertex has a degree of " + degree +
                    "\n\tIts connections are: " + neighbors(vertex) + "\n";
        }
        return ret;
    }

    public static void main(String[] args) {}
}