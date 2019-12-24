package com.kodilla.erenovation.view.main;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.view.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class MainPage extends Div {

    private static final String APP_DESCRIPTION_SUMMARY = "Application Description";
    private static final String APP_DESCRIPTION_TEXT = "This is the app for online pricing and ordering " +
            "renovation services.";
    private static final String PRICING_INSTRUCTION_SUMMARY = "Pricing Instruction";
    private static final String PRICING_INSTRUCTION_TEXT = "To calculate price of renovation services go to " +
            "the pricing tab and fill in the form.";
    private static final String RESERVATION_INSTRUCTION_TEXT = "Registration Instruction";
    private static final String RESERVATION_INSTRUCTION_SUMMARY = "You can organize a meeting with our team " +
            "to confirm the scope of renovation services necessary. The team will come to the address provided " +
            "by you to review the building / apartment.";
    private static final String ACTIVE_USER = "Active user: ";
    private static final String SPACE = " ";

    private final ERenovationClient eRenovationClient;
    private MainView mainView;
    private VerticalLayout verticalLayout;
    private RegistrationForm registrationForm;
    private LoginForm loginForm;
    private AccordionPanel panel;
    private Button logoutButton;

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
        Details appDescription = new Details(APP_DESCRIPTION_SUMMARY, new Text(APP_DESCRIPTION_TEXT));
        verticalLayout.add(appDescription);

        Details pricingInstruction = new Details(PRICING_INSTRUCTION_SUMMARY, new Text(PRICING_INSTRUCTION_TEXT));
        verticalLayout.add(pricingInstruction);

        Details registrationInstruction = new Details(RESERVATION_INSTRUCTION_TEXT,
                new Text(RESERVATION_INSTRUCTION_SUMMARY));
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
        eRenovationClient.deleteService();
    }

    public void setPanel() {
        panel = new AccordionPanel();
        panel.setSummaryText(ACTIVE_USER + getMainView().getUserAuthentication().getName() + SPACE
                + getMainView().getUserAuthentication().getSurname());

        logoutButton = new Button("Log out");
        logoutButton.addClickListener(e -> logOut());

        panel.addContent(logoutButton);
        verticalLayout.add(panel);
    }
}
