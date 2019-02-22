package com.amityprojectvivekrai.amityproject;

import com.google.firebase.database.Exclude;
//

/**
 * Created by MR VIVEK RAI on 15-06-2018.
 */

public class Upload {

    private String name;
    private String imageUrl;
    private String mkey;
    private String videoId;
    private String notesLink;
    private String content;
    private String date;
    private String time;
    private String venue;
    private String description;
    private String authorName;
    private String amazonLink;
    private String contactNumber;

    public Upload() {
    }

    public Upload(String name, String imageUrl, String videoId, String notesLink, String content, String date, String time, String venue, String description, String authorName, String contactNumber, String amazonLink) {
        if (name.trim() == ""){
            name = "No Name";
        }
        this.name = name;
        this.imageUrl = imageUrl;
        this.videoId = videoId;
        this.notesLink = notesLink;
        this.content = content;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.description = description;
        this.authorName = authorName;
        this.contactNumber = contactNumber;
        this.amazonLink = amazonLink;
    }

    public String getName() {
        return name;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotesLink() {
        return notesLink;
    }

    public void setNotesLink(String notesLink) {
        this.notesLink = notesLink;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Exclude
    public String getMkey() {
        return mkey;
    }

    @Exclude
    public void setMkey(String mkey) {
        this.mkey = mkey;
    }
}
