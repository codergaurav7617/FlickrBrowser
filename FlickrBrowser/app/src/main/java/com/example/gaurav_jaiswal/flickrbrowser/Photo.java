package com.example.gaurav_jaiswal.flickrbrowser;

import java.io.Serializable;

/**
 * Created by gaurav_jaiswal on 7/3/17.
 */

class Photo implements Serializable {

    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mTags;
    private String mImage;

    public Photo(String Title, String Author, String AuthorId, String Link, String Tags, String Image) {
        this.mTitle = Title;
        this.mAuthor = Author;
        this.mAuthorId = AuthorId;
        this.mLink = Link;
        this.mTags = Tags;
        this.mImage = Image;
    }

    String getTitle() {
        return mTitle;
    }

     String getAuthor() {
        return mAuthor;
    }

     String getAuthorId() {
        return mAuthorId;
    }

     String getLink() {
        return mLink;
    }

     String getTags() {
        return mTags;
    }

     String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
