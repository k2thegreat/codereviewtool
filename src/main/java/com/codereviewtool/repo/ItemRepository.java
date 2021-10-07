package com.codereviewtool.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.codereviewtool.repo.model.items;

@Repository
public interface ItemRepository extends MongoRepository<items, String>{
    @Query("{name:'?0'}")
    items findItemByName(String name);

}
