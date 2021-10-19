package com.codereviewtool.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.codereviewtool.repo.model.Suggestion;

@Repository
public interface SuggestionRepository extends MongoRepository<Suggestion, String> {
    @Query("{type:'?0'}")
    Page<Suggestion> findSuggestionByType(String type, Pageable pageable);

//    @Query(value = "{'Suggestions.type': {$regex: ?0, $options: 'i'}, 'sourceDescriptor': ?1}", count = true)
    List<Suggestion> findByType(String type);

}
