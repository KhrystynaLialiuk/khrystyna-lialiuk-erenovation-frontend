package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.CreatedPricingDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AllPricingArea extends VerticalLayout {

    private final ERenovationClient eRenovationClient;
    private PricingPage pricingPage;
    private Button openPricingButton;

    private Grid<CreatedPricingDto> pricingGrid = new Grid<>(CreatedPricingDto.class);

    public AllPricingArea(PricingPage pricingPage, ERenovationClient eRenovationClient) {
        this.pricingPage = pricingPage;
        this.eRenovationClient = eRenovationClient;
        setPricingGrid();
    }

    private void setPricingGrid() {
        Details allPricings = new Details("List of pricings",
                new Text("Here is the list of all the pricings you have created"));
        allPricings.setOpened(true);
        pricingGrid.setColumns("id", "date", "price");
        pricingGrid.asSingleSelect().addValueChangeListener(e -> selectPosition());
        openPricingButton = new Button("Open pricing");
        openPricingButton.setEnabled(false);
        openPricingButton.addClickListener(e -> openPricing());
        add(allPricings, pricingGrid, openPricingButton);
        refreshPricingGrid();
    }

    public void refreshPricingGrid() {
        pricingGrid.setItems(eRenovationClient.getPricingDtoList());
    }

    public void selectPosition() {
        CreatedPricingDto selectedPricing = pricingGrid.asSingleSelect().getValue();
        pricingPage.setCurrentPricingId(selectedPricing.getId());
        openPricingButton.setEnabled(true);
    }

    public void openPricing() {
        pricingPage.getCreatePricingButton().setVisible(false);
        pricingPage.getFormArea().setVisible(true);
        pricingPage.refresh();
    }
}

