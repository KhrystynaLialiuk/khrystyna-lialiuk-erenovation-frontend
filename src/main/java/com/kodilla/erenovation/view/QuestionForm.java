package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.QuestionDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class QuestionForm extends FormLayout {

    private final ERenovationClient eRenovationClient;
    private QuestionPage questionPage;
    private Button sendButton;
    private QuestionDto questionDto = new QuestionDto();

    private TextField question = new TextField("Question");

    private Binder<QuestionDto> questionBinder = new Binder<>(QuestionDto.class);

    public QuestionForm(ERenovationClient eRenovationClient, QuestionPage questionPage) {
        this.eRenovationClient = eRenovationClient;
        this.questionPage = questionPage;
        questionBinder.bindInstanceFields(this);
        questionBinder.setBean(questionDto);
        build();
    }

    private void build() {
        sendButton = new Button("Send");
        sendButton.addClickListener(e -> createQuestion());

        add(question, sendButton);
    }

    private void createQuestion() {
        QuestionDto questionDto = questionBinder.getBean();
        questionDto.setUserId(questionPage.getMainView().getUserAuthentication().getId());
        HttpStatus status = eRenovationClient.createQuestion(questionDto);
        if (status.value() == 201) {
            Notification.show("Successful!");
        } else {
            Notification.show("Failed :(  Please check and try again!");
        }
        this.setVisible(false);
        questionPage.getCreateQuestionButton().setVisible(true);
    }
}
