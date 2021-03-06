package com.kodilla.erenovation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("price")
    private String price;

    @JsonProperty("pricingRecordDtos")
    private List<PricingPositionDto> pricingPositionDtoList;
}
