package com.appirio.mapreduce.skills.pojo;

/**
 * Created by parthshah on 8/24/15.
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class MappedSkill {

    @JsonProperty
    @Getter
    @Setter
    private long tagId;

    @JsonProperty
    @Getter
    @Setter
    private SkillSource source;

    @JsonProperty
    @Getter
    @Setter
    private double weight;

}
