package com.quizapp.quizapplication.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.quizapp.quizapplication.model.Quiz;

public interface QuizRepo extends MongoRepository<Quiz,String>{

}