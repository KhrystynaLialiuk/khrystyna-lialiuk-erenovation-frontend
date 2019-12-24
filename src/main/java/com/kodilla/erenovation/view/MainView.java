package com.kodilla.erenovation.view;

import com.kodilla.erenovation.authentification.UserAuthentification;
import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.view.main.MainPage;
import com.kodilla.erenovation.view.pricing.PricingPage;
import com.kodilla.erenovation.view.question.QuestionPage;
import com.kodilla.erenovation.view.reservation.ReservationPage;
import com.kodilla.erenovation.view.user.UserPage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route
public class MainView extends VerticalLayout {

    private static final String APP_TITLE = "ERenovation" ;
    private final ERenovationClient eRenovationClient;

    private UserAuthentification userAuthentication = new UserAuthentification();
    private Tab mainTab;
    private Tab userTab;
    private Tab pricingTab;
    private Tab reservationTab;
    private Tab questionTab;

    public MainView(ERenovationClient eRenovationClient) {
        this.eRenovationClient = eRenovationClient;

        Text appTitle = new Text(APP_TITLE);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        add(appTitle, horizontalLayout);

        mainTab = new Tab("Main Page");
        userTab = new Tab("My Page");
        userTab.setEnabled(false);
        pricingTab = new Tab("Pricing");
        pricingTab.setEnabled(false);
        reservationTab = new Tab("Reservation");
        reservationTab.setEnabled(false);
        questionTab = new Tab("Q&A");
        questionTab.setEnabled(false);
        Tabs tabs = new Tabs(mainTab, userTab, pricingTab, reservationTab, questionTab);

        MainPage mainPage = new MainPage(this, eRenovationClient);
        UserPage userPage = new UserPage(this, eRenovationClient);
        PricingPage pricingPage = new PricingPage(this, eRenovationClient);
        ReservationPage reservationPage = new ReservationPage(this, eRenovationClient);
        QuestionPage questionPage = new QuestionPage(this, eRenovationClient);
        Div pages = new Div(mainPage, userPage, pricingPage, reservationPage, questionPage);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(mainTab, mainPage);
        tabsToPages.put(userTab, userPage);
        tabsToPages.put(pricingTab,pricingPage);
        tabsToPages.put(reservationTab, reservationPage);
        tabsToPages.put(questionTab, questionPage);

        Set<Component> pagesShown = Stream.of(mainPage)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        horizontalLayout.add(tabs, pages);
    }

    public void enableTabs() {
        userTab.setEnabled(true);
        pricingTab.setEnabled(true);
        reservationTab.setEnabled(true);
        questionTab.setEnabled(true);
    }

    public void disableTabs() {
        userTab.setEnabled(false);
        pricingTab.setEnabled(false);
        reservationTab.setEnabled(false);
        questionTab.setEnabled(false);
    }

    public UserAuthentification getUserAuthentication() {
        return userAuthentication;
    }
}
