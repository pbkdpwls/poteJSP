package com.example.potejsp.vote;

public class Food {

    public int foodId;

    public String name;

    public boolean decision;

    public int voteId;

    public Food(String name, boolean decision, int voteId) {
        this.name = name;
        this.decision = decision;
        this.voteId = voteId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }
}
