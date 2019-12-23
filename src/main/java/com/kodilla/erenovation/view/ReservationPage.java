package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class ReservationPage extends Div {

    private final ERenovationClient eRenovationClient;
    private MainView mainView;
    private VerticalLayout verticalLayout;
    private ReservationLayout reservationLayout;

    public ReservationPage(MainView mainView, ERenovationClient eRenovationClient) {
        this.eRenovationClient = eRenovationClient;
        this.mainView = mainView;
        build();
    }

    private void build() {
        verticalLayout = new VerticalLayout();
        reservationLayout = new ReservationLayout(eRenovationClient, this);
        verticalLayout.add(reservationLayout);
        add(verticalLayout);
        setVisible(false);
    }
}
