package com.appirio.mapreduce.skills.pojo;

/**
 * Created by parthshah on 8/24/15.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class MappedSkill {

    @JsonProperty
    @Getter
    @Setter
    private long tagId;

    @JsonProperty
    @Getter
    @Setter
    private Set<SkillSource> sources;

    @JsonProperty
    @Getter
    @Setter
    private double weight;

    @JsonProperty
    @Getter
    @Setter
    private boolean hidden;
}
