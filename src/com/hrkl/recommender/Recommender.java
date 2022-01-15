package com.hrkl.recommender;

import com.hrkl.ISolution;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Recommender implements ISolution {
    public void solve() throws IOException {

        Scanner inFile = new Scanner(new FileReader("src/com/hrkl/recommender/input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result.txt"));


        var su = inFile.nextLine().split("\s");
        var numStories = Integer.parseInt(su[0]);
        var numUsers = Integer.parseInt(su[1]);
        var users = new HashMap<Integer, User>();

        for (var i = 1; i <= numUsers; i++) {
            users.put(i, new User());
        }
        for (var i = 1; i <= numStories; i++) {
            var author = Integer.parseInt(inFile.nextLine());
            users.get(author ).addStory(i);
        }


        var flw = inFile.nextLine().split("\s");
        var numFollowedStories = Integer.parseInt(flw[1]);
        var numFollowedUsers = Integer.parseInt(flw[0]);
        for (var i = 0; i < numFollowedUsers; i++) {
            var flwU = inFile.nextLine().split("\s");
            var follower = Integer.parseInt(flwU[1]);
            var followed = Integer.parseInt(flwU[0]);
            users.get(follower ).addFollowedUser(followed);
            users.get(followed ).addFollowedUser(follower);
        }

        for (var i = 0; i < numFollowedStories; i++) {
            var flwS = inFile.nextLine().split("\s");
            var follower = Integer.parseInt(flwS[0]);
            var followed = Integer.parseInt(flwS[1]);
            users.get(follower ).addFollowedStory(followed);
        }

        int[][] recMatrix = new int[numUsers][numStories];
        for (var i = 1; i <= numUsers; i++) {
            var usr = users.get(i);
            for (var j = 1; j <= numStories; j++) {
                var score = 0;
                if (usr.getStories().contains(j)) {
                    score = -1;

                }
                else if(usr.followsStory(j)){
                    score = -1;
                }

                else{
                    for (var k = 1; k <= numUsers; k++) {
                        var userMultiplier = 1;
                        if (i == k) {
                            userMultiplier = 0;
                        } else if (usr.follows(k)) {
                            userMultiplier = 3;
                        } else if (usr.followsStories(users.get(k).getStories())) {
                            userMultiplier = 2;
                        } else if (usr.followsStories(users.get(k).getFollowedStories())) {
                            userMultiplier = 1;
                        } else {
                            userMultiplier = 0;
                        }

                        var storyMultiplier = 1;

                        if (users.get(k).isAuthorOf(j)) {
                            storyMultiplier = 2;
                        } else if (users.get(k).followsStory(j)) {
                            storyMultiplier = 1;
                        } else {
                            storyMultiplier = 0;
                        }
                        score = score + userMultiplier * storyMultiplier;
                    }
                }


                recMatrix[i-1][j-1] = score;
            }
        }

        for (var i = 0; i < numUsers; i++) {

            bufferedWriter.write(users.get(i).printRecommendedStories());
            bufferedWriter.newLine();
        }


        inFile.close();
        bufferedWriter.close();
    }
}
