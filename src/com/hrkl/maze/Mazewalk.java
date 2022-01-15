package com.hrkl.maze;

import java.util.ArrayList;
import java.util.LinkedList;

public class Mazewalk {
    int[][] recMatrix;
    ArrayList<ArrayList<Integer>> adj;
    int start;
    int end;
    int numberOfVertices;

    public Mazewalk(int[][] recMatrix, int numberOfVertices, int start, int end) {
        this.recMatrix = recMatrix;
        this.start = start - 1;
        this.end = end - 1;
        this.numberOfVertices = numberOfVertices;
        this.adj = new ArrayList<>(numberOfVertices);

        for (int i = 0; i < numberOfVertices; i++) {
            adj.add(new ArrayList<>());
        }

        var width = recMatrix.length;
        var height = recMatrix[width - 1].length;
        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                var node = i * width + j;
                // down
                if (i + 1 < width) {
                    if (recMatrix[i][j] == 0 && recMatrix[i + 1][j] == 0) {
                        addEdge(adj, node, (i + 1) * 10 + j);
                    }
                }
                //  right
                if (j + 1 < height) {
                    if (recMatrix[i][j] == 0 && recMatrix[i][j + 1] == 0) {
                        addEdge(adj, node, (i) * 10 + j + 1);
                    }
                }
                // up
                if (i - 1 >= 0) {
                    if (recMatrix[i][j] == 0 && recMatrix[i - 1][j] == 0) {
                        addEdge(adj, node, (i - 1) * 10 + j);
                    }
                }
                // left
                if (j - 1 >= 0) {
                    if (recMatrix[i][j] == 0 && recMatrix[i][j - 1] == 0) {
                        addEdge(adj, node, (i) * 10 + j - 1);
                    }
                }
            }
        }

    }


    private void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) {
        adj.get(i).add(j);
        adj.get(j).add(i);
    }

    // function to print the shortest distance and path
    // between source vertex and destination vertex
    public void printShortestDistance() {
        var s = this.start;
        var dest = this.end;
        var v = this.numberOfVertices;

        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        int pred[] = new int[v];
        int dist[] = new int[v];

        if (BFS(adj, s, dest, v, pred, dist) == false) {
            System.out.println("Given source and destination" + "are not connected");
            return;
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        // Print distance
        System.out.println("Shortest path length is: " + dist[dest]);

        // Print path
        System.out.println("Path is ::");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
    }

    // a modified version of BFS that stores predecessor
    // of each vertex in array pred
    // and its distance from source in array dist
    private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src, int dest, int v, int pred[], int dist[]) {
        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Integer type
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean visited[] = new boolean[v];

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    // stopping condition (when we find
                    // our destination)
                    if (adj.get(u).get(i) == dest) return true;
                }
            }
        }
        return false;
    }


}
