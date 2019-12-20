package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.CreatedPricingDto;
import com.kodilla.erenovation.dto.response.PricingPositionDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class PricingPage extends Div {

    private final ERenovationClient eRenovationClient;
    private MainView mainView;
    private VerticalLayout verticalLayout;
    private PricingForm pricingForm;
    private String currentPricingId;
    private Button createPricingButton;
    private Text totalPrice;
    private Text pricingIdInformation;
    private VerticalLayout formArea;
    private AllPricingArea allPricingArea;
    private Grid<PricingPositionDto> grid = new Grid<>(PricingPositionDto.class);

    public PricingPage(MainView mainView, ERenovationClient eRenovationClient) {
        this.mainView = mainView;
        this.eRenovationClient = eRenovationClient;
        build();
    }

    private void build() {
        verticalLayout = new VerticalLayout();
        setInstruction();
        setPricingForm();
        setCreatePricingButton();
        allPricingArea = new AllPricingArea(this, eRenovationClient);
        verticalLayout.add(allPricingArea);
        add(verticalLayout);
        setVisible(false);
    }

    private void setInstruction() {
        Details instruction = new Details("Input instruction",
                new Text("Please press 'Create new pricing sheet' button to create a new pricing." +
                        " Add a service to the table by choosing type of service and quantity" +
                        " (items or meters) in correspondent fields. After finishing press 'Calculate' button " +
                        "to calculate the total price and save the pricing."));
        instruction.setOpened(true);
        verticalLayout.add(instruction);
    }

    private void setPricingForm() {
        formArea = new VerticalLayout();
        formArea.setVisible(false);
        pricingForm = new PricingForm(this, eRenovationClient);
        totalPrice = new Text("");
        pricingIdInformation = new Text("");

        formArea.add(totalPrice, pricingIdInformation, pricingForm);
        verticalLayout.add(formArea);
    }

    private void setCreatePricingButton() {
        createPricingButton = new Button("Create new pricing sheet");
        createPricingButton.addClickListener(e -> createPricingSheet());
        verticalLayout.add(createPricingButton);
    }

    private void setGrid() {
        grid.setColumns("service", "quantityOrMeters", "priceType", "price");
        formArea.add(grid);
        refresh();
        grid.asSingleSelect().addValueChangeListener(e -> pricingForm.selectPosition());
    }


    public void refresh() {
        CreatedPricingDto currentPricingDto = eRenovationClient.getPricingDto(currentPricingId);
        grid.setItems(currentPricingDto.getPricingPositionDtoList());
        totalPrice.setText("Total price: " + currentPricingDto.getPrice() + ". ");
    }

    private void createPricingSheet() {
        pricingForm.setComboBox();
        createPricingButton.setVisible(false);
        CreatedPricingDto createdPricingDto = eRenovationClient.createPricing(mainView.getUserAuthentication().getId());
        currentPricingId = createdPricingDto.getId();
        totalPrice.setText("Total price: 0.                      ");
        pricingIdInformation.setText("The ID of the pricing is " + currentPricingId +
                ". You will need it if you want to make a reservation");
        formArea.setVisible(true);
        setGrid();
    }

    public String getCurrentPricingId() {
        return currentPricingId;
    }

    public void setCurrentPricingId(String currentPricingId) {
        this.currentPricingId = currentPricingId;
    }

    public Grid<PricingPositionDto> getGrid() {
        return grid;
    }

    public Button getCreatePricingButton() {
        return createPricingButton;
    }

    public VerticalLayout getFormArea() {
        return formArea;
    }

    public AllPricingArea getAllPricingArea() {
        return allPricingArea;
    }
}
