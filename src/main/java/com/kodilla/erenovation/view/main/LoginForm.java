package com.kodilla.erenovation.view.main;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.LoginDetailsDto;
import com.kodilla.erenovation.dto.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class LoginForm extends FormLayout {

    private final ERenovationClient eRenovationClient;
    private MainPage mainPage;
    private LoginDetailsDto loginDetailsDto = new LoginDetailsDto();
    private Binder<LoginDetailsDto> loginBinder = new Binder<>(LoginDetailsDto.class);

    private TextField emailField = new TextField("User's email:");
    private TextField passwordField = new TextField("Password:");

    public LoginForm(MainPage mainPage, ERenovationClient eRenovationClient) {
        this.mainPage = mainPage;
        this.eRenovationClient = eRenovationClient;
        loginBinder.bindInstanceFields(this);
        loginBinder.setBean(loginDetailsDto);
        build();
    }

    private void build() {
        emailField.setRequired(true);
        passwordField.setRequired(true);
        Button loginButton = new Button("Login");
        loginButton.addClickListener(e -> login());
        Button goToRegistrationButton = new Button("Go to registration");
        goToRegistrationButton.addClickListener(e -> goToRegistration());
        add(emailField, passwordField, loginButton, goToRegistrationButton);
    }

    private void login() {
        LoginDetailsDto loginDetailsDto = loginBinder.getBean();
        UserDto userDto = eRenovationClient.validateUser(loginDetailsDto);
        if (userDto.getId() > 0) {
            rememberUser(userDto);
            mainPage.getMainView().enableTabs();
            this.setVisible(false);
            loginBinder.setBean(new LoginDetailsDto());
            Notification.show("You are logged in :)");
            mainPage.setPanel();
            eRenovationClient.createServices();
        } else {
            Notification.show("Error, please try again!");
            loginBinder.setBean(new LoginDetailsDto());
        }
    }

    private void rememberUser(UserDto userDto) {
        mainPage.getMainView().getUserAuthentication().setId(userDto.getId());
        mainPage.getMainView().getUserAuthentication().setName(userDto.getName());
        mainPage.getMainView().getUserAuthentication().setSurname(userDto.getSurname());
        mainPage.getMainView().getUserAuthentication().setPasswordId(userDto.getUserPasswordDto().getId());
        mainPage.getMainView().getUserAuthentication().setAddressId(userDto.getUserAddressDto().getId());
    }

    private void goToRegistration() {
        mainPage.getRegistrationForm().setVisible(true);
    }
}
