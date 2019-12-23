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
public class ReservationDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("pricingId")
    private String pricingId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("transportationCost")
    private String transportationCost;

    @JsonProperty("reservationAddressDto")
    private ReservationAddressDto reservationAddressDto;
}
