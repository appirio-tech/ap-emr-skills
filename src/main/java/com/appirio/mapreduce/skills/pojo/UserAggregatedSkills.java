package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by parthshah on 8/24/15.
 */
public class UserAggregatedSkills {

    @JsonProperty
    @Getter
    @Setter
    private long userId;

    @JsonProperty
    @Getter
    private String userHandle;

    @Getter
    @JsonProperty
    private String handleLower;

    @Getter
    @Setter
    private String skills;

    public void setUserHandle(String handle) {
        this.userHandle = handle;
        this.handleLower = handle.toLowerCase();
    }

    public UserAggregatedSkills() {}

    public UserAggregatedSkills(long userId, String handle, String skills) {
        this.userId = userId;
        this.userHandle = handle;
        this.handleLower = handle.toLowerCase();
        this.skills = skills;
    }

    public UserAggregatedSkills(long userId, String skills) {
        this.userId = userId;
        this.skills = skills;
    }
}
