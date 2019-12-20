package com.kodilla.erenovation.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ReservationPage extends Div {
    public ReservationPage() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(new Text("Reservation"));
        add(verticalLayout);
        setVisible(false);
    }
}
