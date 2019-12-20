package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.UserPasswordDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class PasswordForm extends FormLayout {

    private ERenovationClient eRenovationClient;
    private PasswordInformationLayout passwordInformationLayout;
    private Button saveButton;

    private UserPasswordDto userPasswordDto = new UserPasswordDto();
    private Binder<UserPasswordDto> passwordBinder = new Binder<>(UserPasswordDto.class);

    private TextField password = new TextField("Password");

    public PasswordForm(ERenovationClient eRenovationClient, PasswordInformationLayout passwordInformationLayout) {
        this.eRenovationClient = eRenovationClient;
        this.passwordInformationLayout = passwordInformationLayout;
        passwordBinder.bindInstanceFields(this);
        passwordBinder.setBean(userPasswordDto);
        build();
    }

    private void build() {
        saveButton = new Button("Save");
        saveButton.addClickListener(e -> savePassword());
        add(password, saveButton);
    }

    private void savePassword() {
        UserPasswordDto passwordToBeSaved = passwordBinder.getBean();
        eRenovationClient.updatePassword(passwordToBeSaved);
        passwordInformationLayout.refreshPasswordGrid();
        Notification.show("Updated");
        this.setVisible(false);
        passwordInformationLayout.getEditPasswordButton().setEnabled(false);
    }

    public void select() {
        passwordBinder.setBean(passwordInformationLayout.getPasswordGrid().asSingleSelect().getValue());
    }
}
