package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by parthshah on 8/23/15.
 */


public class UserEnteredSkillRecord {
    @JsonProperty
    @Getter
    @Setter
    private long userId;

    @JsonProperty
    @Getter
    @Setter
    private String userHandle;

    @JsonBackReference
    @Getter
    @Setter
    private List<UserEnteredSkill> skills;

}
