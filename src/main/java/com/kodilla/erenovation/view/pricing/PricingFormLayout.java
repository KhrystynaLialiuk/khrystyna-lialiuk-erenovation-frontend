package com.kodilla.erenovation.view.pricing;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.PricingDto;
import com.kodilla.erenovation.dto.PricingPositionDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class PricingFormLayout extends VerticalLayout {

    private static final String INSTRUCTION_SUMMARY = "Input instruction";
    private static final String INSTRUCTION_TEXT = "Please press 'Create new pricing sheet' button to create " +
            "a new pricing.  Add a service to the table by choosing type of service and quantity" +
            " (items or meters) in correspondent fields. After finishing press 'Calculate' button " +
            "to calculate the total price and save the pricing. \n Remember to add position before saving";
    private static final String STARTING_TOTAL_PRICE = "Total price: 0.                      ";
    private static final String TOTAL_PRICE = "Total price: ";
    private static final String PRICING_ID = "The ID of the pricing is ";
    private static final String PRICING_ID_NOTE = ". You will need it if you want to make a reservation" ;
    private static final String SPACE = ". ";

    private final ERenovationClient eRenovationClient;
    private PricingPage pricingPage;

    private PricingForm pricingForm;
    private Button createPricingButton;
    private Text totalPrice;
    private Text pricingId;
    private HorizontalLayout pricingInformation;
    private Grid<PricingPositionDto> grid = new Grid<>(PricingPositionDto.class);

    public PricingFormLayout(ERenovationClient eRenovationClient, PricingPage pricingPage) {
        this.eRenovationClient = eRenovationClient;
        this.pricingPage = pricingPage;
        build();
    }

    private void build() {
        setInstruction();
        setPricingInformation();
        setPricingForm();
        setCreatePricingButton();
        setGrid();
    }

    private void setInstruction() {
        Details instruction = new Details(INSTRUCTION_SUMMARY, new Text(INSTRUCTION_TEXT));
        instruction.setOpened(true);
        add(instruction);
    }

    private void setPricingInformation() {
        pricingInformation = new HorizontalLayout();
        totalPrice = new Text("");
        pricingId = new Text("");
        pricingInformation.add(totalPrice, pricingId);
        pricingInformation.setVisible(false);
        add(pricingInformation);
    }

    private void setPricingForm() {
        pricingForm = new PricingForm(this, eRenovationClient);
        pricingForm.setVisible(false);
        add(pricingForm);
    }

    private void setGrid() {
        grid.setColumns("service", "quantityOrMeters", "priceType", "price");
        add(grid);
        grid.setVisible(false);
        grid.asSingleSelect().addValueChangeListener(e -> pricingForm.selectPosition());
    }

    private void setCreatePricingButton() {
        createPricingButton = new Button("Create new pricing sheet");
        createPricingButton.addClickListener(e -> createPricingSheet());
        add(createPricingButton);
    }

    private void createPricingSheet() {
        pricingForm.setComboBox();
        createPricingButton.setVisible(false);
        PricingDto pricingDto = eRenovationClient.createPricing(pricingPage.getMainView()
                .getUserAuthentication().getId());
        pricingPage.setCurrentPricingId(pricingDto.getId());
        totalPrice.setText(STARTING_TOTAL_PRICE);
        pricingId.setText(PRICING_ID + pricingPage.getCurrentPricingId() + PRICING_ID_NOTE);
        pricingInformation.setVisible(true);
        pricingForm.setVisible(true);
        grid.setVisible(true);
        refresh();
    }

    public void refresh() {
        PricingDto currentPricingDto = eRenovationClient.getPricingDto(pricingPage.getCurrentPricingId());
        grid.setItems(currentPricingDto.getPricingPositionDtoList());
        totalPrice.setText(TOTAL_PRICE + currentPricingDto.getPrice() + SPACE);
    }
}
