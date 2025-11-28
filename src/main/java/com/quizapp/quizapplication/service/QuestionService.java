package com.quizapp.quizapplication.service;

import com.quizapp.quizapplication.model.Question;
import com.quizapp.quizapplication.repo.QuestionRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }
}
