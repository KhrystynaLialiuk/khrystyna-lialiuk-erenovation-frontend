package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.PricingPositionDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class PricingForm extends FormLayout {

    private final ERenovationClient eRenovationClient;
    private PricingFormLayout pricingFormLayout;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button saveButton;

    private PricingPositionDto pricingPositionDto = new PricingPositionDto();
    private Binder<PricingPositionDto> pricingBinder = new Binder<>(PricingPositionDto.class);

    private ComboBox<String> service = new ComboBox<>("Service");
    private TextField quantityOrMeters = new TextField("Quantity or meters");

    public PricingForm(PricingFormLayout pricingFormLayout, ERenovationClient eRenovationClient) {
        this.eRenovationClient = eRenovationClient;
        this.pricingFormLayout = pricingFormLayout;
        pricingBinder.bindInstanceFields(this);
        pricingBinder.setBean(pricingPositionDto);
        build();
    }

    public void setComboBox() {
        List<String> services = eRenovationClient.getListOfServiceTitles();
        service.setItems(services);
    }

    private void build() {
        service.setRequired(true);
        quantityOrMeters.setRequired(true);

        addButton = new Button("Add position");
        updateButton = new Button("Update position");
        deleteButton = new Button("Delete position");
        saveButton = new Button("Save pricing");

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        saveButton.setEnabled(false);

        addButton.addClickListener(e -> addPosition());
        updateButton.addClickListener(e -> updatePosition());
        deleteButton.addClickListener(e -> deletePosition());
        saveButton.addClickListener(e -> savePricing());

        add(service, quantityOrMeters, addButton, updateButton, deleteButton, saveButton);
    }

    private void addPosition() {
        PricingPositionDto pricingPositionDto = pricingBinder.getBean();
        pricingPositionDto.setPricingId(pricingFormLayout.getPricingPage().getCurrentPricingId());
        HttpStatus status = eRenovationClient.createPricingPosition(pricingPositionDto);
        if (status.value() == 201) {
            Notification.show("Successful!");
            pricingBinder.setBean(new PricingPositionDto());
        } else {
            Notification.show("Failed :(  Please try again!");
        }
        pricingFormLayout.refresh();
        saveButton.setEnabled(true);
    }

    private void updatePosition() {
        PricingPositionDto pricingPositionDto = pricingBinder.getBean();
        eRenovationClient.updatePricingPosition(pricingPositionDto);
        clear();
    }

    private void deletePosition() {
        PricingPositionDto pricingPositionDto = pricingBinder.getBean();
        eRenovationClient.deletePricingPosition(pricingPositionDto);
        clear();
    }

    private void savePricing() {
        Notification.show("Pricing has been saved. You can now see it in the list of all your pricings.");
        pricingFormLayout.getCreatePricingButton().setVisible(true);
        pricingFormLayout.getPricingInformation().setVisible(false);
        pricingFormLayout.getGrid().setVisible(false);
        pricingFormLayout.getPricingPage().getAllPricingsLayout().getPricingGrid().asSingleSelect().clear();
        pricingFormLayout.getPricingPage().getAllPricingsLayout().refreshPricingGrid();
        pricingFormLayout.getPricingPage().getAllPricingsLayout().getOpenPricingButton().setEnabled(false);
        setVisible(false);
    }

    public void selectPosition() {
        pricingBinder.setBean(pricingFormLayout.getGrid().asSingleSelect().getValue());
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);
        addButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void clear() {
        pricingFormLayout.getGrid().asSingleSelect().clear();
        pricingBinder.setBean(new PricingPositionDto());
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        saveButton.setEnabled(true);
        pricingFormLayout.refresh();
    }
}
