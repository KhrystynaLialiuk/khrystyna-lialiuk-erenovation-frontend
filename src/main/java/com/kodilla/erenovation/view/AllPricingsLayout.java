package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.CreatedPricingDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class AllPricingsLayout extends VerticalLayout {

    private final ERenovationClient eRenovationClient;
    private PricingPage pricingPage;
    private HorizontalLayout buttons;
    private Button showPricingsButton;
    private Button openPricingButton;
    private Button hidePricingsButton;

    private Grid<CreatedPricingDto> pricingGrid = new Grid<>(CreatedPricingDto.class);

    public AllPricingsLayout(ERenovationClient eRenovationClient, PricingPage pricingPage) {
        this.pricingPage = pricingPage;
        this.eRenovationClient = eRenovationClient;
        build();
    }

    private void build() {
        Details allPricings = new Details("List of pricings",
                new Text("Here is the list of all the pricings you have created"));
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
        pricingGrid.setItems(eRenovationClient.getPricingDtoList());
    }

    public void selectPosition() {
        openPricingButton.setEnabled(true);
        showPricingsButton.setEnabled(false);
    }

    public void openPricing() {
        CreatedPricingDto selectedPricing = pricingGrid.asSingleSelect().getValue();
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
        refreshPricingGrid();
    }

    private void hidePricings() {
        pricingGrid.setVisible(false);
        openPricingButton.setEnabled(false);
        showPricingsButton.setEnabled(true);
        hidePricingsButton.setEnabled(false);
    }
}

