package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by parthshah on 8/24/15.
 */
public class AggregatedSkill {

    @JsonProperty
    @Getter
    @Setter
    private long tagId;

    @JsonProperty
    @Getter
    @Setter
    private double score;

    @JsonProperty
    @Getter
    @Setter
    private Set<SkillSource> sources;

    public AggregatedSkill() {}

    public AggregatedSkill(long tagId, double score, Set<SkillSource> sources) {
        this.tagId = tagId;
        this.score = score;
        this.sources = sources;
    }
}
