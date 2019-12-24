package com.kodilla.erenovation.view.main;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.RegistrationDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

@Getter
public class RegistrationForm extends FormLayout {

    private final ERenovationClient eRenovationClient;
    private final MainPage mainPage;
    private RegistrationDto registrationDto = new RegistrationDto();
    private Binder<RegistrationDto> registrationBinder = new Binder<>(RegistrationDto.class);

    private TextField name = new TextField("Name:");
    private TextField surname = new TextField("Surname:");
    private TextField phone = new TextField("Phone number:");
    private TextField email = new TextField("Email address:");
    private TextField password = new TextField("Password:");
    private TextField city = new TextField("Address - city:");
    private TextField street = new TextField("Address - street:");
    private TextField building = new TextField("Address - building:");
    private TextField apartment = new TextField("Address - apartment:");
    private TextField postalCode = new TextField("Address - postal code:");

    public RegistrationForm(MainPage mainPage, ERenovationClient eRenovationClient) {
        this.mainPage = mainPage;
        this.eRenovationClient = eRenovationClient;
        registrationBinder.bindInstanceFields(this);
        registrationBinder.setBean(registrationDto);
        build();
    }

    private void build() {
        name.setRequired(true);
        surname.setRequired(true);
        phone.setRequired(true);
        email.setRequired(true);
        password.setRequired(true);
        city.setRequired(true);
        street.setRequired(true);
        building.setRequired(true);
        apartment.setRequired(true);
        postalCode.setRequired(true);
        Button registrationButton = new Button("Register");
        registrationButton.addClickListener(e -> register());
        add(name, surname, phone, email, password, city, street, building, apartment, postalCode, registrationButton);
        setVisible(false);
    }

    private void register() {
        RegistrationDto registrationDto = registrationBinder.getBean();
        if ((registrationDto.getEmail().length() == 0) || (registrationDto.getPassword().length() == 0)) {
            Notification.show("Email and password fields are mandatory!");
        } else {
            if (eRenovationClient.createUser(registrationDto).value() == 201) {
                Notification.show("Registration was successful! You can now login!");
                this.setVisible(false);
                registrationBinder.setBean(new RegistrationDto());
            } else {
                Notification.show("Registration failed :(  Please try again!");
            }
        }
    }
}
