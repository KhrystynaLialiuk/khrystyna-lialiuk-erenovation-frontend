package com.kodilla.erenovation.view.pricing;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.PricingPositionDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Getter
public class PricingForm extends FormLayout {

    private static final String SUCCESSFUL = "Successful!";
    private static final String FAILED = "Failed :(  Please try again!";
    private static final String EMPTY_CHOICE = "You may have tries to add empty choice";
    private static final String PRICING_SAVED = "Pricing has been saved. You can now see it in the " +
            "list of all your pricings.";

    private final ERenovationClient eRenovationClient;
    private PricingFormLayout pricingFormLayout;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button saveButton;

    private PricingPositionDto pricingPositionDto = new PricingPositionDto();
    private Binder<PricingPositionDto> pricingBinder = new Binder<>(PricingPositionDto.class);

    private ComboBox<String> service = new ComboBox<>("Service");
    private TextField quantityOrMeters = new TextField("Quantity or meters, integer value");

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
        try {
            HttpStatus status = eRenovationClient.createPricingPosition(pricingPositionDto);
            if (status.value() == 201) {
                Notification.show(SUCCESSFUL);
                pricingBinder.setBean(new PricingPositionDto());
            } else {
                Notification.show(FAILED);
            }
            pricingFormLayout.refresh();
            saveButton.setEnabled(true);
        } catch (HttpClientErrorException e) {
            Notification.show(EMPTY_CHOICE);
        }
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
        Notification.show(PRICING_SAVED);
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
        addButton.setEnabled(true);
        saveButton.setEnabled(true);
        pricingFormLayout.refresh();
    }
}
