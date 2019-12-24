package com.kodilla.erenovation.view.user;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.UserDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class PersonalInformationLayout extends VerticalLayout {

    private static final String PERSONAL_INFORMATION_SUMMARY = "Personal information";
    private static final String PERSONAL_INFORMATION_TEXT = "Here you can view and edit your personal information. " +
            "To edit personal data select it in the grid and press 'Edit personal information' button.";


    private final ERenovationClient eRenovationClient;
    private UserPage userPage;

    private PersonalInformationForm personalInformationForm;
    private Button editPersonalInformationButton;
    private Button showPersonalInformationButton;
    private Button hidePersonalInformationButton;
    private HorizontalLayout buttons;
    private Grid<UserDto> personalInformationGrid = new Grid<>(UserDto.class);

    public PersonalInformationLayout(UserPage userPage, ERenovationClient eRenovationClient) {
        this.userPage = userPage;
        this.eRenovationClient = eRenovationClient;
        build();
    }

    private void build() {
        Details personalInformation = new Details(PERSONAL_INFORMATION_SUMMARY, new Text(PERSONAL_INFORMATION_TEXT));
        personalInformation.setOpened(true);

        buttons = new HorizontalLayout();

        showPersonalInformationButton = new Button("Show personal information");
        showPersonalInformationButton.addClickListener(e -> showPersonalInformationGrid());

        editPersonalInformationButton = new Button("Edit personal information");
        editPersonalInformationButton.setEnabled(false);
        editPersonalInformationButton.addClickListener(e -> showPersonalInformationForm());

        hidePersonalInformationButton = new Button("Hide personal information");
        hidePersonalInformationButton.setEnabled(false);
        hidePersonalInformationButton.addClickListener(e -> hidePersonalInformationGridAndForm());

        personalInformationForm = new PersonalInformationForm(eRenovationClient, this);
        personalInformationForm.setVisible(false);

        buttons.add(showPersonalInformationButton, hidePersonalInformationButton, editPersonalInformationButton);

        add(personalInformation, buttons, personalInformationForm);

        addPersonalInformationGrid();
    }

    private void addPersonalInformationGrid() {
        personalInformationGrid.setColumns("name", "surname", "email", "phone");
        add(personalInformationGrid);
        personalInformationGrid.setVisible(false);
        personalInformationGrid.asSingleSelect().addValueChangeListener(e -> selectPersonalDetails());
    }

    private void showPersonalInformationForm() {
        editPersonalInformationButton.setEnabled(false);
        hidePersonalInformationButton.setEnabled(false);
        personalInformationForm.setVisible(true);
        personalInformationForm.select();
    }

    public void refreshPersonalInformationGrid() {
        personalInformationGrid.setItems(eRenovationClient
                .getUserById(userPage.getMainView().getUserAuthentication().getId()));
    }

    private void showPersonalInformationGrid() {
        personalInformationGrid.setVisible(true);
        hidePersonalInformationButton.setEnabled(true);
        editPersonalInformationButton.setEnabled(false);
        showPersonalInformationButton.setEnabled(false);
        refreshPersonalInformationGrid();
    }

    private void selectPersonalDetails() {
        editPersonalInformationButton.setEnabled(true);
    }

    private void hidePersonalInformationGridAndForm() {
        personalInformationGrid.setVisible(false);
        personalInformationGrid.asSingleSelect().clear();
        hidePersonalInformationButton.setEnabled(false);
        editPersonalInformationButton.setEnabled(false);
        showPersonalInformationButton.setEnabled(true);
        personalInformationForm.setVisible(false);
    }
}
