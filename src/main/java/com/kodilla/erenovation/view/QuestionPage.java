package com.kodilla.erenovation.view;

import com.kodilla.erenovation.client.ERenovationClient;
import com.kodilla.erenovation.dto.response.QuestionDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class QuestionPage extends VerticalLayout {

    private final ERenovationClient eRenovationClient;
    private MainView mainView;
    private VerticalLayout verticalLayout;
    private QuestionForm questionForm;
    private Button createQuestionButton;
    private Button showQuestionsButton;
    private Button hideQuestionsButton;

    private Grid<QuestionDto> questionGrid = new Grid<>(QuestionDto.class);

    public QuestionPage(MainView mainView, ERenovationClient eRenovationClient) {
        this.eRenovationClient = eRenovationClient;
        this.mainView = mainView;
        build();
    }

    private void build() {
        verticalLayout = new VerticalLayout();

        Details questionsInformation = new Details("Questions",
                new Text("Here you can ask a question and see the answer. Questions are usually answered during " +
                        "24 hours."));
        questionsInformation.setOpened(true);

        createQuestionButton = new Button("Create question");
        createQuestionButton.addClickListener(e -> createQuestion());

        questionForm = new QuestionForm(eRenovationClient, this);
        questionForm.setVisible(false);

        showQuestionsButton = new Button("Show all Q&A");
        showQuestionsButton.addClickListener(e -> showQuestions());

        hideQuestionsButton = new Button("Hide Q&A");
        hideQuestionsButton.setEnabled(false);
        hideQuestionsButton.addClickListener(e -> hideQuestions());

        questionGrid.setColumns("id", "question", "date", "answer");
        questionGrid.setVisible(false);

        verticalLayout.add(questionsInformation, createQuestionButton, questionForm, showQuestionsButton, hideQuestionsButton,
                questionGrid);
        add(verticalLayout);
        setVisible(false);
    }

    public void createQuestion() {
        createQuestionButton.setVisible(false);
        questionForm.setVisible(true);
    }

    private void showQuestions() {
        questionGrid.setItems(eRenovationClient.getQuestions(mainView.getUserAuthentication().getId()));
        questionGrid.setVisible(true);
        hideQuestionsButton.setEnabled(true);
        showQuestionsButton.setEnabled(false);
    }

    private void hideQuestions() {
        hideQuestionsButton.setEnabled(false);
        showQuestionsButton.setEnabled(true);
        questionGrid.setVisible(false);
    }
}
