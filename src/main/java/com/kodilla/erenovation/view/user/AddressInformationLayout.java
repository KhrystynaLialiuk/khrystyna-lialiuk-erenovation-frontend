package com.kodilla.erenovation.view.user;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.UserAddressDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class AddressInformationLayout extends VerticalLayout {

    private static final String ADDRESS_INFORMATION_SUMMARY = "Address information";
    private static final String ADDRESS_INFORMATION_TEXT = "Here you can view and edit your address. " +
            "To edit the address select it in the grid and press 'Edit address' button.";

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
        Details addressInformation = new Details(ADDRESS_INFORMATION_SUMMARY, new Text(ADDRESS_INFORMATION_TEXT));
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
        showAddressButton.setEnabled(false);
        refreshAddressGrid();
    }

    private void showAddressForm() {
        editAddressButton.setEnabled(false);
        hideAddressButton.setEnabled(false);
        addressForm.setVisible(true);
        addressForm.select();
    }

    private void hideAddressGridAndForm() {
        addressGrid.setVisible(false);
        addressGrid.asSingleSelect().clear();
        hideAddressButton.setEnabled(false);
        editAddressButton.setEnabled(false);
        showAddressButton.setEnabled(true);
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
