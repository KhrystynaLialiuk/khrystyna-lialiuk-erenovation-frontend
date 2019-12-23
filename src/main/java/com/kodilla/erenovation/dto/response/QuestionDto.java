package com.kodilla.erenovation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("question")
    private String question;

    @JsonProperty("date")
    private String date;

    @JsonProperty("answer")
    private String answer;
}
