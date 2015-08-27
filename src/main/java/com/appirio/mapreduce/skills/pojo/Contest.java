package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by parthshah on 8/26/15.
 */
public class Contest {

    @JsonProperty
    @Getter
    @Setter
    private long externalId;

    @JsonProperty
    @Getter
    @Setter
    private ChallengeType type;

    @JsonProperty
    @Getter
    @Setter
    private double weight;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
    @Getter
    @Setter
    private Date createdAt;

}
