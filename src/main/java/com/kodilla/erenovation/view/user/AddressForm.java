package com.kodilla.erenovation.view.user;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.UserAddressDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AddressForm extends FormLayout {

    private ERenovationClient eRenovationClient;
    private AddressInformationLayout addressInformationLayout;
    private Button saveButton;

    private UserAddressDto useraddressDto = new UserAddressDto();
    private Binder<UserAddressDto> addressBinder = new Binder<>(UserAddressDto.class);

    private TextField city = new TextField("city");
    private TextField street = new TextField("street");
    private TextField building = new TextField("building");
    private TextField apartment = new TextField("apartment");
    private TextField postalCode = new TextField("postalCode");

    public AddressForm(ERenovationClient eRenovationClient, AddressInformationLayout addressInformationLayout) {
        this.eRenovationClient = eRenovationClient;
        this.addressInformationLayout = addressInformationLayout;
        addressBinder.bindInstanceFields(this);
        addressBinder.setBean(useraddressDto);
        build();
    }

    private void build() {
        saveButton = new Button("Save");
        saveButton.addClickListener(e -> saveAddress());
        add(city, street, building, apartment, postalCode, saveButton);
    }

    private void saveAddress() {
        UserAddressDto userAddressToBeSaved = addressBinder.getBean();
        eRenovationClient.updateAddress(userAddressToBeSaved);
        addressInformationLayout.refreshAddressGrid();
        Notification.show("Updated");
        this.setVisible(false);
        addressInformationLayout.getHideAddressButton().setEnabled(true);
        addressInformationLayout.getEditAddressButton().setEnabled(false);
    }

    public void select() {
        addressBinder.setBean(addressInformationLayout.getAddressGrid().asSingleSelect().getValue());
    }
}
