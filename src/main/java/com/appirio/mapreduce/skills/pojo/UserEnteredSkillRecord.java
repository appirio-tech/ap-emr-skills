package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by parthshah on 8/23/15.
 */


public class UserEnteredSkillRecord {
    @JsonProperty("userid")
    @Getter
    @Setter
    private long userId;

    @JsonProperty("userhandle")
    @Getter
    @Setter
    private String userHandle;

    @JsonBackReference("skills")
    @Getter
    @Setter
    private String skills;
}
