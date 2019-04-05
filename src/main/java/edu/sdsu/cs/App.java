/*
****Program 3****

Huy Tran
    cssc0763
Alex Gutierrez
    cssc0784
*/

package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.*;

import java.io.*;
import java.util.*;


public class App {
    private static IGraph<String> graph;

    public static void main(String[] args) {
        graph = new DirectedGraph<>();

        File myFile;
        if(args.length == 0){
            myFile = new File("C:\\Users\\tranq\\IdeaProjects" +
                    "\\Prog3\\src\\main\\java\\edu\\sdsu\\cs\\layout.csv");
            add(myFile);
            System.out.println(graph.toString());
        }
        else{
            myFile = new File(args[0]);
            add(myFile);
            System.out.println(graph.toString());
        }

        System.out.println("Shortest path:");
        String start = "Quang Tri";
        String dest = "Binh Duong";
        System.out.println(graph.shortestPath(start,dest));
    }

    private static FileReader reader(String line) {
        try {
            return new FileReader(line);
        } catch (IOException e) {
            System.out.println(line + "Unable to open filename. Verify the " +
                    "file exists, is\n" +
                    "accessible, and meets the syntax requirements.\n");
            System.exit(-1);
        }
        return null;
    }

    private static void add(File file) {
        Scanner sc;
        try {
            sc = new Scanner(file);
            sc.useDelimiter(",");
            while (sc.hasNext()) {
                String trueRowCol = sc.nextLine().trim();
                String[] dimToken = trueRowCol.split(",");
                String trueRowStr = dimToken[0];
                if (dimToken.length == 1) {
                    graph.add(trueRowStr);
                }
                if (dimToken.length == 2) {
                    String trueColStr = dimToken[1];
                    if (!graph.contains(trueRowStr)) {
                        graph.add(trueRowStr);
                    }
                    if (!graph.contains(trueColStr)) {
                        graph.add(trueColStr);
                    }
                    graph.connect(trueRowStr, trueColStr);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
