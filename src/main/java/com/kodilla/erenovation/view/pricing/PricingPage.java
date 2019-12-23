package com.kodilla.erenovation.view.pricing;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.view.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class PricingPage extends Div {

    private final ERenovationClient eRenovationClient;
    private MainView mainView;
    private VerticalLayout verticalLayout;
    private PricingFormLayout pricingFormLayout;
    private AllPricingsLayout allPricingsLayout;
    private String currentPricingId;

    public PricingPage(MainView mainView, ERenovationClient eRenovationClient) {
        this.mainView = mainView;
        this.eRenovationClient = eRenovationClient;
        build();
    }

    private void build() {
        verticalLayout = new VerticalLayout();
        pricingFormLayout = new PricingFormLayout(eRenovationClient, this);
        allPricingsLayout = new AllPricingsLayout(eRenovationClient, this);
        verticalLayout.add(pricingFormLayout, allPricingsLayout);
        add(verticalLayout);
        setVisible(false);
    }

    public String getCurrentPricingId() {
        return currentPricingId;
    }

    public void setCurrentPricingId(String currentPricingId) {
        this.currentPricingId = currentPricingId;
    }
}
