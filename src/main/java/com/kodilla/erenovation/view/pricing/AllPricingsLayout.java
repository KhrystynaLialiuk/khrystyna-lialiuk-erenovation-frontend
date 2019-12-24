package com.kodilla.erenovation.view.pricing;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.PricingDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class AllPricingsLayout extends VerticalLayout {

    private final static String ALL_PRICING_SUMMARY = "List of pricings";
    private final static String ALL_PRICING_TEXT = "Here is the list of all the pricings you have created";

    private final ERenovationClient eRenovationClient;
    private PricingPage pricingPage;
    private HorizontalLayout buttons;
    private Button showPricingsButton;
    private Button openPricingButton;
    private Button hidePricingsButton;

    private Grid<PricingDto> pricingGrid = new Grid<>(PricingDto.class);

    public AllPricingsLayout(ERenovationClient eRenovationClient, PricingPage pricingPage) {
        this.pricingPage = pricingPage;
        this.eRenovationClient = eRenovationClient;
        build();
    }

    private void build() {
        Details allPricings = new Details(ALL_PRICING_SUMMARY, new Text(ALL_PRICING_TEXT));
        allPricings.setOpened(true);
        add(allPricings);
        createButtons();
        setPricingGrid();
    }

    private void createButtons() {
        buttons = new HorizontalLayout();

        showPricingsButton = new Button("Show all my pricings");
        showPricingsButton.addClickListener(e -> showPricings());

        openPricingButton = new Button("Open pricing");
        openPricingButton.setEnabled(false);
        openPricingButton.addClickListener(e -> openPricing());

        hidePricingsButton = new Button("Hide list of pricings");
        hidePricingsButton.setEnabled(false);
        hidePricingsButton.addClickListener(e -> hidePricings());

        buttons.add(showPricingsButton, hidePricingsButton, openPricingButton);
        add(buttons);
    }

    private void setPricingGrid() {
        pricingGrid.setColumns("id", "date", "price");
        pricingGrid.asSingleSelect().addValueChangeListener(e -> selectPosition());
        pricingGrid.setVisible(false);
        add(pricingGrid);
    }

    public void refreshPricingGrid() {
        pricingGrid.setItems(eRenovationClient.getPricingDtoList(
                pricingPage.getMainView().getUserAuthentication().getId()));
    }

    public void selectPosition() {
        openPricingButton.setEnabled(true);
        showPricingsButton.setEnabled(false);
    }

    public void openPricing() {
        PricingDto selectedPricing = pricingGrid.asSingleSelect().getValue();
        pricingPage.setCurrentPricingId(selectedPricing.getId());
        pricingGrid.asSingleSelect().clear();
        pricingPage.getPricingFormLayout().getCreatePricingButton().setVisible(false);
        pricingPage.getPricingFormLayout().getPricingInformation().setVisible(true);
        pricingPage.getPricingFormLayout().getPricingForm().setVisible(true);
        pricingPage.getPricingFormLayout().getGrid().setVisible(true);
        pricingPage.getPricingFormLayout().refresh();
        pricingPage.getPricingFormLayout().getPricingForm().getAddButton().setEnabled(true);
    }

    private void showPricings() {
        pricingGrid.setVisible(true);
        hidePricingsButton.setEnabled(true);
        showPricingsButton.setEnabled(false);
        openPricingButton.setEnabled(false);
        refreshPricingGrid();
    }

    private void hidePricings() {
        pricingGrid.setVisible(false);
        pricingGrid.asSingleSelect().clear();
        openPricingButton.setEnabled(false);
        showPricingsButton.setEnabled(true);
        hidePricingsButton.setEnabled(false);
    }
}

