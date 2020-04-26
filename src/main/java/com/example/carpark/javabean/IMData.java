package com.example.carpark.javabean;

import java.util.List;

public class IMData {

    private UserEntity mine;
    private UserEntity to;
    private List<Friend> friend;


    public UserEntity getTo() {
        return to;
    }

    public void setTo(UserEntity to) {
        this.to = to;
    }

    public UserEntity getMine() {
        return mine;
    }

    public void setMine(UserEntity mine) {
        this.mine = mine;
    }

    public List<Friend> getFriend() {
        return friend;
    }

    public void setFriend(List<Friend> friend) {
        this.friend = friend;
    }
}
