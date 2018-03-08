package com.tecelm.hasna.mostsarredgithubrepos;

/**
 * Created by Admin on 23/02/2018.
 */

public class Repository {

    String reponame;
    String description;
    String avatarurl;
    String ownername;
    Long starscount;

    public Repository(String reponame, String description, String avatarurl, String ownername, Long starscount) {
        this.reponame = reponame;
        this.description = description;
        this.avatarurl = avatarurl;
        this.ownername = ownername;
        this.starscount = starscount;
    }

    public Repository() {
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getReponame() {
        return reponame;
    }

    public void setReponame(String reponame) {
        this.reponame = reponame;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStarscount() {
        return starscount;
    }

    public void setStarscount(Long starscount) {
        this.starscount = starscount;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }
}
