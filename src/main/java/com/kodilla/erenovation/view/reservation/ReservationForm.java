package com.kodilla.erenovation.view.reservation;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.ReservationDto;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

@Getter
public class ReservationForm extends FormLayout {

    private final ERenovationClient eRenovationClient;
    private ReservationLayout reservationLayout;
    private ReservationDto reservationDto = new ReservationDto();

    private TextField pricingId = new TextField("Pricing ID");
    private TextField date = new TextField("Desirable date in format yyyy-mm-dd");

    private Binder<ReservationDto> reservationBinder = new Binder<>(ReservationDto.class);

    public ReservationForm(ERenovationClient eRenovationClient, ReservationLayout reservationLayout) {
        this.eRenovationClient = eRenovationClient;
        this.reservationLayout = reservationLayout;
        reservationBinder.bindInstanceFields(this);
        reservationBinder.setBean(reservationDto);
        add(pricingId, date);
    }
}
