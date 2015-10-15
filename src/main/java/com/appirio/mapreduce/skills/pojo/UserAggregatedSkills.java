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

    @Getter
    @Setter
    private String skills;

    public UserAggregatedSkills() {}

    public UserAggregatedSkills(long userId, String skills) {
        this.userId = userId;
        this.skills = skills;
    }
}
