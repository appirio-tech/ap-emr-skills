package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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

    public AggregatedSkill() {}

    public AggregatedSkill(long tagId, double score) {
        this.tagId = tagId;
        this.score = score;
    }
}
