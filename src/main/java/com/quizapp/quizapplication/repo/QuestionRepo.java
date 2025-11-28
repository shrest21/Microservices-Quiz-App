package com.quizapp.quizapplication.repo;

import com.quizapp.quizapplication.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepo extends MongoRepository<Question, String> {
}
