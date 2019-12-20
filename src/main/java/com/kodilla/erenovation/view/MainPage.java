package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class MainPage extends Div {

    private final ERenovationClient eRenovationClient;
    private MainView mainView;
    private VerticalLayout verticalLayout;
    private RegistrationForm registrationForm;
    private LoginForm loginForm;
    private AccordionPanel panel;
    //private ConfirmDialog confirmation;
    private Button logoutButton;
    private Button deleteAccountButton;

    public MainPage(MainView mainView, ERenovationClient eRenovationClient) {
        this.mainView = mainView;
        this.eRenovationClient = eRenovationClient;
        build();
    }

    private void build() {
        verticalLayout = new VerticalLayout();
        add(verticalLayout);
        setVisible(true);
        showDetails();
        setRegistrationForm();
        setLoginForm();
    }

    private void showDetails() {
        Details appDescription = new Details("Application Description",
                new Text("This is the app for online pricing and ordering renovation services."));
        verticalLayout.add(appDescription);

        Details pricingInstruction = new Details("Pricing Instruction",
                new Text("To calculate price of renovation services go to the pricing tab and fill in the form."));
        verticalLayout.add(pricingInstruction);

        Details registrationInstruction = new Details("Registration Instruction",
                new Text("You can organize a meeting with our team to confirm the scope of renovation services necessary." +
                        " The team will come to the address provided by you to review the building / apartment."));
        verticalLayout.add(registrationInstruction);

        appDescription.setOpened(true);
        pricingInstruction.setOpened(true);
        registrationInstruction.setOpened(true);
    }

    private void setRegistrationForm() {
        registrationForm = new RegistrationForm(this, eRenovationClient);
        verticalLayout.add(registrationForm);
    }

    private void setLoginForm() {
        loginForm = new LoginForm(this, eRenovationClient);
        verticalLayout.add(loginForm);
    }

    private void logOut() {
        getMainView().getUserAuthentication().setId(0);
        getMainView().getUserAuthentication().setName(null);
        getMainView().getUserAuthentication().setSurname(null);
        getMainView().disableTabs();
        loginForm.setVisible(true);
        panel.setVisible(false);
    }

    private void deleteAccount() {
        eRenovationClient.deleteUserById(mainView.getUserAuthentication().getId());
        logOut();
    }

    //private void cancelDeletion() {
    //  confirmation.close();
    //}

    public void setPanel() {
        panel = new AccordionPanel();
        panel.setSummaryText("Active user: " + getMainView().getUserAuthentication().getName() + " "
                + getMainView().getUserAuthentication().getSurname());

        /*confirmation = new ConfirmDialog("Account Deletion!",
                "Are you sure you want to delete your account?", "Delete", e -> deleteAccount(),
                "Cancel", e -> cancelDeletion());*/

        logoutButton = new Button("Log out");
        logoutButton.addClickListener(e -> logOut());

        deleteAccountButton = new Button("Delete Account");
        deleteAccountButton.addClickListener(e -> deleteAccount());

        panel.addContent(logoutButton, deleteAccountButton);
        verticalLayout.add(panel);
    }
}
