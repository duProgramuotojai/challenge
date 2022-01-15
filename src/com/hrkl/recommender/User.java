package com.hrkl.recommender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private List<Integer> stories;
    private List<Integer> followedStories;
    private List<Integer> followedUsers;
    private List<Integer> recommendedStories;

    public User() {
        stories = new ArrayList<>();
        followedStories = new ArrayList<>();
        followedUsers = new ArrayList<>();
        recommendedStories = new ArrayList<>();
    }

    public List<Integer> getStories() {
        return stories;
    }

    public List<Integer> getFollowedStories() {
        return followedStories;
    }

    public List<Integer> getFollowedUsers() {
        return followedUsers;
    }

    public List<Integer> getRecommendedStories() {
        return recommendedStories;
    }

    public void setStories(List<Integer> stories) {
        this.stories = stories;
    }


    public void setFollowedStories(List<Integer> followedStories) {
        this.followedStories = followedStories;
    }

    public void setFollowedUsers(List<Integer> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public void setRecommendedStories(List<Integer> recommendedStories) {
        this.recommendedStories = recommendedStories;
    }

    public void addStory(int story) {
        stories.add(story);
    }

    public void addFollowedStory(int story) {
        followedStories.add(story);
    }

    public void addFollowedUser(int user) {
        followedUsers.add(user);
    }

    public void addRecommendedStory(int story) {
        recommendedStories.add(story);
    }

    public boolean follows(int user) {
        return followedUsers.contains(user);
    }

    public boolean followsStories(List<Integer> stories) {
        List<Integer> others = stories.stream()
            .filter(i -> followedStories.contains(i))
            .collect(Collectors.toList());

        return others.size() > 0;
    }

    public boolean followsStory(int story) {
        return followedStories.contains(story);
    }

    public boolean isAuthorOf(int story) {
        return stories.contains(story);
    }


    public String printRecommendedStories() {
        if (recommendedStories.size() < 3) {
            return "not enough recommended stories";
        }
        return String.format("%s  %s  %s", recommendedStories.get(0), recommendedStories.get(1), recommendedStories.get(2));
    }

}
