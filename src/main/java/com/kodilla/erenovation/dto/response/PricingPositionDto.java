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
public class PricingPositionDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("quantityOrMeters")
    private String quantityOrMeters;

    @JsonProperty("priceType")
    private String priceType;

    @JsonProperty("price")
    private String price;

    @JsonProperty("serviceTitle")
    private String service;

    @JsonProperty("serviceId")
    private String serviceId;

    @JsonProperty("pricingId")
    private String pricingId;

}
