package com.kodilla.erenovation.view.user;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.UserPasswordDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class PasswordInformationLayout extends VerticalLayout {

    private final ERenovationClient eRenovationClient;
    private UserPage userPage;
    private HorizontalLayout buttons;
    private Button showPasswordButton;
    private Button editPasswordButton;
    private Button hidePasswordButton;
    private PasswordForm passwordForm;
    private Grid<UserPasswordDto> passwordGrid = new Grid<>(UserPasswordDto.class);

    public PasswordInformationLayout(UserPage userPage, ERenovationClient eRenovationClient) {
        this.eRenovationClient = eRenovationClient;
        this.userPage = userPage;
        build();
    }

    private void build() {
        Details passwordInformation = new Details("Password information",
                new Text("Here you can view and change your password. To edit the password select" +
                        " it in the grid and press 'Edit password' button."));
        passwordInformation.setOpened(true);

        buttons = new HorizontalLayout();

        showPasswordButton = new Button("Show password");
        showPasswordButton.addClickListener(e -> showPasswordGrid());

        editPasswordButton = new Button("Edit password");
        editPasswordButton.setEnabled(false);
        editPasswordButton.addClickListener(e -> showPasswordForm());

        hidePasswordButton = new Button("Hide password");
        hidePasswordButton.setEnabled(false);
        hidePasswordButton.addClickListener(e -> hidePasswordGridAndForm());

        buttons.add(showPasswordButton, hidePasswordButton, editPasswordButton);

        passwordForm = new PasswordForm(eRenovationClient, this);
        passwordForm.setVisible(false);

        add(passwordInformation, buttons, passwordForm);

        addPasswordGrid();
    }

    private void addPasswordGrid() {
        passwordGrid.setColumns("password");
        add(passwordGrid);
        passwordGrid.setVisible(false);
        passwordGrid.asSingleSelect().addValueChangeListener(e -> selectPassword());
    }

    private void showPasswordGrid() {
        passwordGrid.setVisible(true);
        hidePasswordButton.setEnabled(true);
        editPasswordButton.setEnabled(false);
        showPasswordButton.setEnabled(false);
        refreshPasswordGrid();
    }

    private void showPasswordForm() {
        editPasswordButton.setEnabled(false);
        hidePasswordButton.setEnabled(false);
        passwordForm.setVisible(true);
        passwordForm.select();
    }

    private void hidePasswordGridAndForm() {
        passwordGrid.setVisible(false);
        passwordGrid.asSingleSelect().clear();
        hidePasswordButton.setEnabled(false);
        editPasswordButton.setEnabled(false);
        showPasswordButton.setEnabled(true);
        passwordForm.setVisible(false);
    }

    public void refreshPasswordGrid() {
        passwordGrid.setItems(eRenovationClient
                .getPasswordById(userPage.getMainView().getUserAuthentication().getPasswordId()));
    }

    private void selectPassword() {
        editPasswordButton.setEnabled(true);
    }
}
