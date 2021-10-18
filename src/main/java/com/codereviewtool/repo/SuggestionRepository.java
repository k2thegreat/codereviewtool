package com.codereviewtool.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.codereviewtool.repo.model.Suggestion;

@Repository
public interface SuggestionRepository extends MongoRepository<Suggestion, String> {
    @Query("{type:'?0'}")
    List<Suggestion> findSuggestionByType(String type);

}
