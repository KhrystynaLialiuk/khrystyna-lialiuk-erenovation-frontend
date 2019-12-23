package com.kodilla.erenovation.view.reservation;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.ReservationDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class ReservationLayout extends VerticalLayout {

    private final ERenovationClient eRenovationClient;
    private ReservationPage reservationPage;
    private ReservationForm reservationForm;
    private ReservationAddressFrom reservationAddressFrom;
    private Button createReservationButton;
    private HorizontalLayout buttons;
    private Button showReservationsButton;
    private Button hideReservationsButton;
    private Button seeReservationButton;
    private Button editReservationButton;
    private Button deleteReservationButton;

    private Grid<ReservationDto> reservationGrid = new Grid<>(ReservationDto.class);

    public ReservationLayout(ERenovationClient eRenovationClient, ReservationPage reservationPage) {
        this.eRenovationClient = eRenovationClient;
        this.reservationPage = reservationPage;
        build();
    }

    private void build() {
        Details reservationInstruction = new Details("Reservation information",
                new Text("If you have doubts about your pricing, here you can make a reservation " +
                        "for a visit by a professional, who will be able to " +
                        "assess the scope of work and help you to calculate the whole cost of potential renovation, " +
                        " inform you about time necessary for renovation and possible start date if applicable. \n" +
                        "Please fill the form with correspond data. The cost of visit is 50 PLN plus cost of " +
                        "transportation to the indicate address. You can see transportation cost before saving " +
                        "reservation by clicking 'Calculate transportation cost' button. " +
                        "If the cost of transportation equals 0, the " +
                        "application may have failed to find the indicated address: check the provided address, " +
                        "try to enter address without special symbols, enter other closest city " +
                        "or contact us via phone or email."));
        reservationInstruction.setOpened(true);

        createReservationButton = new Button("Create reservation");
        createReservationButton.addClickListener(e -> createReservation());

        reservationForm = new ReservationForm(eRenovationClient, this);
        reservationForm.setVisible(false);

        reservationAddressFrom = new ReservationAddressFrom(eRenovationClient, this);
        reservationAddressFrom.setVisible(false);

        buttons = new HorizontalLayout();

        showReservationsButton = new Button("Show my reservations");
        showReservationsButton.addClickListener(e -> showReservations());

        hideReservationsButton = new Button("Hide list of reservations");
        hideReservationsButton.addClickListener(e -> hideReservations());
        hideReservationsButton.setEnabled(false);

        seeReservationButton = new Button("Open reservation");
        seeReservationButton.addClickListener(e -> seeReservation());
        seeReservationButton.setEnabled(false);

        editReservationButton = new Button("Edit reservation");
        editReservationButton.addClickListener(e -> editReservation());
        editReservationButton.setEnabled(false);

        deleteReservationButton = new Button("Delete reservation");
        deleteReservationButton.setEnabled(false);
        deleteReservationButton.addClickListener(e -> deleteReservation());

        buttons.add(showReservationsButton, hideReservationsButton, seeReservationButton,
                editReservationButton, deleteReservationButton);

        reservationGrid = new Grid<>(ReservationDto.class);
        reservationGrid.setColumns("id", "pricingId", "date", "transportationCost");
        reservationGrid.asSingleSelect().addValueChangeListener(e -> selectReservation());
        reservationGrid.setVisible(false);

        add(reservationInstruction, createReservationButton, reservationForm, reservationAddressFrom,
                buttons, reservationGrid);
    }

    private void createReservation() {
        reservationForm.setVisible(true);
        reservationAddressFrom.setVisible(true);
        createReservationButton.setVisible(false);
    }

    private void showReservations() {
        reservationGrid.setVisible(true);
        refreshReservationGrid();
        showReservationsButton.setEnabled(false);
        hideReservationsButton.setEnabled(true);
    }

    public void refreshReservationGrid() {
        reservationGrid.setItems(eRenovationClient.getReservations(
                reservationPage.getMainView().getUserAuthentication().getId()));
        seeReservationButton.setEnabled(false);
        editReservationButton.setEnabled(false);
        deleteReservationButton.setEnabled(false);
    }

    private void hideReservations() {
        showReservationsButton.setEnabled(true);
        hideReservationsButton.setEnabled(false);
        seeReservationButton.setEnabled(false);
        editReservationButton.setEnabled(false);
        deleteReservationButton.setEnabled(false);
        reservationGrid.setVisible(false);
    }

    private void selectReservation() {
        seeReservationButton.setEnabled(true);
        editReservationButton.setEnabled(true);
        deleteReservationButton.setEnabled(true);
    }

    private void seeReservation() {
        ReservationDto reservationDto = reservationGrid.asSingleSelect().getValue();
        reservationGrid.asSingleSelect().clear();
        reservationForm.getReservationBinder().setBean(reservationDto);
        reservationAddressFrom.getReservationAddressBinder().setBean(reservationDto.getReservationAddressDto());
        createReservationButton.setVisible(false);
        reservationForm.setVisible(true);
        reservationAddressFrom.setVisible(true);
        reservationAddressFrom.getSaveButton().setEnabled(false);
        reservationAddressFrom.getTransportationCostButton().setEnabled(false);
        refreshReservationGrid();
    }

    private void editReservation() {
        seeReservation();
        reservationAddressFrom.getSaveButton().setEnabled(true);
        reservationAddressFrom.getTransportationCostButton().setEnabled(true);
        refreshReservationGrid();
    }

    private void deleteReservation() {
        ReservationDto reservationDto = reservationGrid.asSingleSelect().getValue();
        reservationGrid.asSingleSelect().clear();
        eRenovationClient.deleteReservation(reservationDto);
        refreshReservationGrid();
    }
}
