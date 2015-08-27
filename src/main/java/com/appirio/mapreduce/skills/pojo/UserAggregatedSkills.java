package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<AggregatedSkill> skills;


    public UserAggregatedSkills() {}

    public UserAggregatedSkills(long userId, String handle, List<AggregatedSkill> skills) {
        this.userId = userId;
        this.userHandle = handle;
        this.skills = skills;
    }
}
