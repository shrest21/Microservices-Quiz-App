package com.quizapp.quizapplication.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizapp.quizapplication.model.Question;
import com.quizapp.quizapplication.model.QuestionWrapper;
import com.quizapp.quizapplication.model.Quiz;
import com.quizapp.quizapplication.model.Response;
import com.quizapp.quizapplication.repo.QuestionRepo;
import com.quizapp.quizapplication.repo.QuizRepo;

@Service
public class QuizService {
    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<Quiz> createQuiz(String category, int numQ, String title) {
        Quiz quiz= new Quiz();
        quiz.setTitle(title);
        List<Question> questions= questionRepo.findRandomQuestionByCategory(category,numQ);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);
        return new ResponseEntity<>(quiz,HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(String quizId) {

        Optional<Quiz> quizOpt = quizRepo.findById(quizId);

        if (quizOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 if quiz doesn't exist
        }

        Quiz quiz = quizOpt.get();

        // Convert each Question into a QuestionWrapper
        List<QuestionWrapper> wrappedQuestions = quiz.getQuestions()
                .stream()
                .map(q -> new QuestionWrapper(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(wrappedQuestions);
    }

    public ResponseEntity<Integer> submitQuiz(String quizId, List<Response> responses) {
        Optional<Quiz> quizOpt = quizRepo.findById(quizId);

        if (quizOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Quiz quiz = quizOpt.get();
        List<Question> questions = quiz.getQuestions();

        // Convert responses list to a Map for quick lookup: {questionId -> userAnswer}
        Map<String, String> responseMap = responses.stream()
                .collect(Collectors.toMap(Response::getId, Response::getRightAnswer));

        int score = 0;

        // Compare correct answers
        for (Question q : questions) {
            String userAnswer = responseMap.get(q.getId());
            if (userAnswer != null && userAnswer.equalsIgnoreCase(q.getRightAnswer())) {
                score++;
            }
        }

        return ResponseEntity.ok(score);
    }



}