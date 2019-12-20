package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class UserPage extends Div {

    private final ERenovationClient eRenovationClient;
    private MainView mainView;
    private VerticalLayout verticalLayout;
    private PersonalInformationLayout personalInformationLayout;
    private PasswordInformationLayout passwordInformationLayout;
    private AddressInformationLayout addressInformationLayout;


    public UserPage(MainView mainView, ERenovationClient eRenovationClient) {
        this.mainView = mainView;
        this.eRenovationClient = eRenovationClient;
        build();
    }

    private void build() {
        verticalLayout = new VerticalLayout();
        personalInformationLayout = new PersonalInformationLayout(this, eRenovationClient);
        passwordInformationLayout = new PasswordInformationLayout(this, eRenovationClient);
        addressInformationLayout = new AddressInformationLayout(this, eRenovationClient);
        verticalLayout.add(personalInformationLayout, passwordInformationLayout, addressInformationLayout);
        add(verticalLayout);
        setVisible(false);
    }
}
