package com.quizapp.quizapplication.model;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "questions")
public class Question {

    @Id
    private String id;

    private String questionTitle;
    private List<String> options;
    private String rightAnswer;
    private String difficultyLevel;
}