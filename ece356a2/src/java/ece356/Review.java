/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

/**
 *
 * @author steal_000
 */
public class Review {
    private int revID;
    private String date;
    private int starRating;
    private String comments;

    public Review() {
    }

    public Review(int revID, String date, int starRating, String comments) {
        this.revID = revID;
        this.date = date;
        this.starRating = starRating;
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public int getStarRating() {
        return starRating;
    }

    public String getComments() {
        return comments;
    }

    public int getRevID() {
        return revID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRevID(int revID) {
        this.revID = revID;
    }
}
