package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

@Getter
public class PersonalInformationForm extends FormLayout {

    private ERenovationClient eRenovationClient;
    private PersonalInformationLayout personalInformationLayout;
    private Button saveButton;

    private UserDto userDto = new UserDto();
    private Binder<UserDto> personalInformationBinder = new Binder<>(UserDto.class);

    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField phone = new TextField("Phone");
    private TextField email = new TextField("Email");

    public PersonalInformationForm(ERenovationClient eRenovationClient, PersonalInformationLayout personalInformationLayout) {
        this.eRenovationClient = eRenovationClient;
        this.personalInformationLayout = personalInformationLayout;
        personalInformationBinder.bindInstanceFields(this);
        personalInformationBinder.setBean(userDto);
        build();
    }

    private void build() {
        saveButton = new Button("Save");
        saveButton.addClickListener(e -> savePersonalDetails());

        add(name, surname, phone, email, saveButton);
    }

    private void savePersonalDetails() {
        UserDto detailsToBeSaved = personalInformationBinder.getBean();
        eRenovationClient.updateUser(detailsToBeSaved);
        personalInformationLayout.refreshPersonalInformationGrid();
        Notification.show("Updated");
        this.setVisible(false);
        personalInformationLayout.getEditPersonalInformationButton().setEnabled(false);
    }

    public void select() {
        personalInformationBinder.setBean(personalInformationLayout.getPersonalInformationGrid().asSingleSelect().getValue());
    }
}
