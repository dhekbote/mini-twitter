package minitwitter.model;

import java.util.ArrayList;
import java.util.List;

import minitwitter.observer.Observer;
import minitwitter.observer.Subject;
import minitwitter.visitor.UserGroupVisitor;


public class User extends UserGroupComponent implements Observer, Subject {
    private String id;
    private List<String> followers;
    private List<String> followings;
    private List<String> newsFeed;
    private List<Observer> observers;
    private List<String> tweets;
    
    public User(String id) {
        this.id = id;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.tweets = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public List<String> getFollowers() {
        return followers;
    }
    
    public List<String> getFollowings() {
        return followings;
    }
    
    public List<String> getTweets() {
        return tweets;
    }
    
    public List<String> getNewsFeed() {
        return newsFeed;
    }
    
    public void follow(User user) {
        if (!followings.contains(user.getId())) {
            followings.add(user.getId());
            user.addFollower(this.id);
            user.registerObserver(this);
        }
    }
    
    public void addFollower(String followerId) {
        if (!followers.contains(followerId)) {
            followers.add(followerId);
        }
    }
    
    public void postTweet(String tweet) {
    	 String tweetWithUser = id + ": " + tweet;
         tweets.add(tweetWithUser);
         newsFeed.add(tweetWithUser);
         notifyObservers(tweetWithUser);
    }
    
    @Override
    public void update(String tweet) {
        newsFeed.add(tweet);
    }
    
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    
    @Override
    public void accept(UserGroupVisitor visitor) {
        visitor.visitUser(this);
    }
    
    @Override
    public void notifyObservers(String tweet) {
    	 for (Observer observer : observers) {
             observer.update(tweet);
         }
    }
}