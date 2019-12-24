package com.kodilla.erenovation.view.reservation;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.ReservationAddressDto;
import com.kodilla.erenovation.dto.ReservationDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

@Getter
public class ReservationAddressFrom extends FormLayout {

    private final ERenovationClient eRenovationClient;
    private ReservationLayout reservationLayout;
    private ReservationAddressDto reservationAddressDto = new ReservationAddressDto();
    private Button saveButton;
    private Button transportationCostButton;
    private Button cancelButton;

    private TextField city = new TextField("City");
    private TextField street = new TextField("Street");
    private TextField building = new TextField("Building");
    private TextField apartment = new TextField("Apartment");
    private TextField postalCode = new TextField("Postal Code");

    private Binder<ReservationAddressDto> reservationAddressBinder = new Binder<>(ReservationAddressDto.class);

    public ReservationAddressFrom(ERenovationClient eRenovationClient, ReservationLayout reservationLayout) {
        this.eRenovationClient = eRenovationClient;
        this.reservationLayout = reservationLayout;
        reservationAddressBinder.bindInstanceFields(this);
        reservationAddressBinder.setBean(reservationAddressDto);
        build();
    }

    private void build() {
        saveButton = new Button("Save");
        saveButton.addClickListener(e -> saveReservation());

        transportationCostButton = new Button("Calculate transportation cost");
        transportationCostButton.addClickListener(e -> getTransportationCost());

        cancelButton = new Button("Cancel");
        cancelButton.addClickListener(e -> clear());

        add(city, street, building, apartment, postalCode, saveButton, transportationCostButton, cancelButton);
    }

    private void saveReservation() {
        ReservationDto reservationDto = reservationLayout.getReservationForm().getReservationBinder().getBean();
        reservationDto.setUserId(reservationLayout.getReservationPage().getMainView().getUserAuthentication().getId());
        BigDecimal transportationCost = eRenovationClient.getTransportationCost(reservationAddressBinder.getBean());
        reservationDto.setTransportationCost(transportationCost.toString());
        reservationDto.setReservationAddressDto(reservationAddressBinder.getBean());
        if (reservationDto.getId() == 0) {
            createNewReservation(reservationDto);
        } else {
            updateExistingReservation(reservationDto);
        }
        reservationLayout.refreshReservationGrid();
    }

    private void getTransportationCost() {
        BigDecimal transportationCost = eRenovationClient.getTransportationCost(reservationAddressBinder.getBean());
        Notification.show("Transportation cost equals " + transportationCost);
    }

    private void clear() {
        reservationAddressBinder.setBean(new ReservationAddressDto());
        reservationLayout.getReservationForm().getReservationBinder().setBean(new ReservationDto());
        reservationLayout.getCreateReservationButton().setVisible(true);
        reservationLayout.getReservationForm().setVisible(false);
        reservationLayout.refreshReservationGrid();
        this.setVisible(false);
    }

    private void createNewReservation(ReservationDto reservationDto) {
        try {
            HttpStatus status = eRenovationClient.createReservation(reservationDto);
            if (status.value() == 201) {
                Notification.show("Successful!");
                clear();
            } else {
                Notification.show("Failed :(  You may have entered not existing pricing ID.");
            }
        } catch (HttpClientErrorException e) {
            Notification.show("You may have entered not existing pricing ID or date is in the wrong format");
        }
    }

    private void updateExistingReservation(ReservationDto reservationDto) {
        try {
            eRenovationClient.updateReservation(reservationDto);
            clear();
        } catch (HttpClientErrorException e) {
            Notification.show("You may have entered not existing pricing ID or date is in the wrong format");
        }
    }
}
