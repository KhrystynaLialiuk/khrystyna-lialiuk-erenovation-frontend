package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.UserAddressDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class AddressInformationLayout extends VerticalLayout {

    private final ERenovationClient eRenovationClient;
    private UserPage userPage;
    private HorizontalLayout buttons;
    private Button showAddressButton;
    private Button editAddressButton;
    private Button hideAddressButton;
    private AddressForm addressForm;
    private Grid<UserAddressDto> addressGrid = new Grid<>(UserAddressDto.class);

    public AddressInformationLayout(UserPage userPage, ERenovationClient eRenovationClient) {
        this.eRenovationClient = eRenovationClient;
        this.userPage = userPage;
        build();
    }

    private void build() {
        Details addressInformation = new Details("Address information",
                new Text("Here you can view and edit your address. To edit the address select" +
                        " it in the grid and press 'Edit address' button."));
        addressInformation.setOpened(true);

        buttons = new HorizontalLayout();

        showAddressButton = new Button("Show address");
        showAddressButton.addClickListener(e -> showAddressGrid());

        editAddressButton = new Button("Edit address");
        editAddressButton.setEnabled(false);
        editAddressButton.addClickListener(e -> showAddressForm());

        hideAddressButton = new Button("Hide address");
        hideAddressButton.setEnabled(false);
        hideAddressButton.addClickListener(e -> hideAddressGridAndForm());

        buttons.add(showAddressButton, hideAddressButton, editAddressButton);

        addressForm = new AddressForm(eRenovationClient, this);
        addressForm.setVisible(false);

        add(addressInformation, buttons, addressForm);

        addAddressGrid();
    }

    private void addAddressGrid() {
        addressGrid.setColumns("city", "street", "building", "apartment", "postalCode");
        add(addressGrid);
        addressGrid.setVisible(false);
        addressGrid.asSingleSelect().addValueChangeListener(e -> selectAddress());
    }

    private void showAddressGrid() {
        addressGrid.setVisible(true);
        hideAddressButton.setEnabled(true);
        editAddressButton.setEnabled(false);
        refreshAddressGrid();
    }

    private void showAddressForm() {
        addressForm.setVisible(true);
        addressForm.select();
    }

    private void hideAddressGridAndForm() {
        addressGrid.setVisible(false);
        hideAddressButton.setEnabled(false);
        editAddressButton.setEnabled(false);
        addressForm.setVisible(false);
    }

    public void refreshAddressGrid() {
        addressGrid.setItems(eRenovationClient
                .getAddressById(userPage.getMainView().getUserAuthentication().getAddressId()));
    }

    private void selectAddress() {
        editAddressButton.setEnabled(true);
    }
}
