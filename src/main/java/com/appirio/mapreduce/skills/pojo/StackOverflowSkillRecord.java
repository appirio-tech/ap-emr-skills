package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rpemmaraju on 9/16/15.
 */


public class StackOverflowSkillRecord {
    @JsonProperty("userid")
    @Getter
    @Setter
    private long userId;

    @JsonBackReference("toptags")
    @Getter
    @Setter
    private String topTags;
}
