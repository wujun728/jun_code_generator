package com.jeasy.mongodb.dao;

import com.jeasy.mongodb.model.WordModel;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * word集合的dao
 */
@Persistent
public interface WordDao extends MongoRepository<WordModel, Long> {

}
