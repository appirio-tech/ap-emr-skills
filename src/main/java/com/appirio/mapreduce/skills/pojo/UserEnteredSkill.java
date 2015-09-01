package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by parthshah on 8/23/15.
 */
public class UserEnteredSkill {

//    @JsonProperty
//    @Getter
//    @Setter
//    private long tagId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
    @Getter
    @Setter
    private Date createdAt;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
    @Getter
    @Setter
    private Date updatedAt;

    @JsonProperty
    @Getter
    @Setter
    private boolean hidden;
}
