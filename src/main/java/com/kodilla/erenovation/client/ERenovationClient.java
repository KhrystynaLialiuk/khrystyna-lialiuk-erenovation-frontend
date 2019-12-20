package com.kodilla.erenovation.client;

import com.kodilla.erenovation.dto.request.LoginDetailsDto;
import com.kodilla.erenovation.dto.request.RegistrationDto;
import com.kodilla.erenovation.dto.response.*;
import com.kodilla.erenovation.config.PropertiesExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ERenovationClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesExtraction propertiesExtraction;

    public HttpStatus createUser(RegistrationDto registrationDto) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationUserApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri();
        ResponseEntity<String> response = restTemplate.postForEntity(uri, registrationDto, String.class);
        return response.getStatusCode();
    }

    public UserDto validateUser(LoginDetailsDto loginDetailsDto) {
        String url = propertiesExtraction.getERenovationBaseApi() +
                propertiesExtraction.getERenovationUserApi() + "/validation";
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("email", loginDetailsDto.getEmailField())
                .queryParam("password", loginDetailsDto.getPasswordField())
                .build().encode().toUri();
        return restTemplate.getForObject(uri, UserDto.class);
    }

    public CreatedPricingDto createPricing(long userId) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationPricingApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId)
                .build().encode().toUri();
        return restTemplate.postForObject(uri, null, CreatedPricingDto.class);
    }

    public HttpStatus createPricingPosition(final PricingPositionDto pricingPositionDto) {
        String url = propertiesExtraction.getERenovationBaseApi()
                + propertiesExtraction.getERenovationPricingPositionApi();
        ResponseEntity<String> response = restTemplate.postForEntity(url, pricingPositionDto, String.class);
        return response.getStatusCode();
    }

    public List<String> getListOfServiceTitles() {
        String url = propertiesExtraction.getERenovationBaseApi()
                + propertiesExtraction.getERenovationServiceApi() + "/titles";
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri();
        String[] services = restTemplate.getForObject(uri, String[].class);
        return Arrays.asList(Optional.ofNullable(services).orElse(new String[0]));
    }

    public CreatedPricingDto getPricingDto(final String currentPricingId) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationPricingApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("pricingId", currentPricingId)
                .build().encode().toUri();
        return restTemplate.getForObject(uri, CreatedPricingDto.class);
    }

    /*public ServiceDto getServiceById(final String serviceId) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationServiceApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceId", serviceId)
                .build().encode().toUri();
        return restTemplate.getForObject(uri, ServiceDto.class);
    }*/

    public void updatePricingPosition(final PricingPositionDto pricingPositionDto) {
        String url = propertiesExtraction.getERenovationBaseApi() +
                propertiesExtraction.getERenovationPricingPositionApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri();
        restTemplate.put(uri, pricingPositionDto);
    }

    public void deletePricingPosition(final PricingPositionDto pricingPositionDto) {
        String url = propertiesExtraction.getERenovationBaseApi() +
                propertiesExtraction.getERenovationPricingPositionApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("pricingRecordId", pricingPositionDto.getId())
                .queryParam("pricingId", pricingPositionDto.getPricingId())
                .build().encode().toUri();
        restTemplate.delete(uri);
    }

    public List<CreatedPricingDto> getPricingDtoList() {
        String url = propertiesExtraction.getERenovationBaseApi()
                + propertiesExtraction.getERenovationPricingApi() + "/all";
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri();
        CreatedPricingDto[] pricingList = restTemplate.getForObject(uri, CreatedPricingDto[].class);
        return Arrays.asList(Optional.ofNullable(pricingList).orElse(new CreatedPricingDto[0]));
    }

    public UserDto getUserById(final long userId) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationUserApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId)
                .build().encode().toUri();
        return restTemplate.getForObject(uri, UserDto.class);
    }

    public void updateUser(final UserDto userDto) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationUserApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri();
        restTemplate.put(uri, userDto);
    }

    public void deleteUserById(final long userId) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationUserApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId)
                .build().encode().toUri();
        restTemplate.delete(uri);
    }

    public UserPasswordDto getPasswordById(final long userPasswordId) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationPasswordApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userPasswordId", userPasswordId)
                .build().encode().toUri();
        return restTemplate.getForObject(uri, UserPasswordDto.class);
    }

    public void updatePassword(final UserPasswordDto userPasswordDto) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationPasswordApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri();
        restTemplate.put(uri, userPasswordDto);
    }

    public UserAddressDto getAddressById(final long userAddressId) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationAddressApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userAddressId", userAddressId)
                .build().encode().toUri();
        return restTemplate.getForObject(uri, UserAddressDto.class);
    }

    public void updateAddress(final UserAddressDto userAddressDto) {
        String url = propertiesExtraction.getERenovationBaseApi() + propertiesExtraction.getERenovationAddressApi();
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri();
        restTemplate.put(uri, userAddressDto);
    }
}
