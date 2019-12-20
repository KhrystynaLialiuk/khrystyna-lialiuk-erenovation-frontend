package com.kodilla.erenovation.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertiesExtraction {
    @Value("${erenovation.base.api}")
    private String eRenovationBaseApi;

    @Value("${api.user}")
    private String eRenovationUserApi;

    @Value("${api.password}")
    private String eRenovationPasswordApi;

    @Value("${api.pricing}")
    private String eRenovationPricingApi;

    @Value("${api.pricingposition}")
    private String eRenovationPricingPositionApi;

    @Value("${api.service}")
    private String eRenovationServiceApi;

    @Value("${api.address}")
    private String eRenovationAddressApi;
}
