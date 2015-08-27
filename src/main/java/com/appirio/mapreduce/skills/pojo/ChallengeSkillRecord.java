package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by parthshah on 8/26/15.
 */
public class ChallengeSkillRecord {

    @JsonProperty
    @Getter
    @Setter
    private long userId;

    @JsonProperty
    @Getter
    @Setter
    private String userHandle;

    @JsonProperty
    @Getter
    @Setter
    private List<ChallengeSkill> skills;
}