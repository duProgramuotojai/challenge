package com.hrkl.maze;

import com.hrkl.ISolution;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Maze implements ISolution {
    @Override
    public void solve() throws IOException {

        Scanner inFile = new Scanner(new FileReader("src/com/hrkl/maze/input_maze2.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result_maze.txt"));

        var dim = Arrays.stream(inFile.nextLine().trim().split("\s")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        var recMatrix = new int[dim.get(0)][dim.get(1)];
        // No of vertices
        int v = dim.get(0) * dim.get(1);

        var source = 0;
        var end = 0;
        for (var j = 0; j < dim.get(1); j++) {
            var line = inFile.nextLine().trim();
            var i = 0;
            for (char ch : line.toCharArray()) {
                if (ch == '#') {
                    recMatrix[i][j] = 1;
                } else {
                    var current = j * dim.get(0) + i + 1;
                    if (ch == 'S') {
                        source = current;
                    } else if (ch == 'E') {
                        end = current;

                    }
                    recMatrix[i][j] = 0;
                }
                i++;
            }
        }

        for (var i = 0; i < dim.get(2); i++) {
            var g = inFile.nextLine().trim().split("\s");
            var x = Integer.parseInt(g[0]);
            var y = Integer.parseInt(g[1]);
            var r = Integer.parseInt(g[2]);
            for (var j = x - r - 1; j < x + r; j++) {
                for (var k = y - r - 1; k < y + r; k++) {
                    recMatrix[k][j] = 1;
                }
            }
        }


        var mw = new Mazewalk(recMatrix, dim.get(0) * dim.get(1), source, end);
        mw.printShortestDistance();
    }
}
