package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by parthshah on 8/26/15.
 */
public class ChallengeSkill {
    @JsonProperty
    @Getter
    @Setter
    private long tagId;

    @Getter
    @Setter
    private List<Contest> contests;
}
