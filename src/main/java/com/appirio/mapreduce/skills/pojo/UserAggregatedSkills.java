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
    @Setter
    private String userHandle;

    @Getter
    @Setter
    private String skills;


    public UserAggregatedSkills() {}

    public UserAggregatedSkills(long userId, String handle, String skills) {
        this.userId = userId;
        this.userHandle = handle;
        this.skills = skills;
    }
}
