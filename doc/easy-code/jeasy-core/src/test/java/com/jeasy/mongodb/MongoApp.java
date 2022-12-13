package com.jeasy.mongodb;

import com.jeasy.common.json.JsonKit;
import com.jeasy.mongodb.model.WordModel;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * 无认证，测试
 */
public class MongoApp {

    private static final Logger log = LoggerFactory.getLogger(MongoApp.class);

    public static void main(String[] args) throws Exception {
        MongoOperations mongoOps = new MongoTemplate(new MongoClient("192.168.1.156", 27017), "autocomlete");
        WordModel word = new WordModel();
        word.setInitials("sj");
        word.setPinyin("shouji");
        word.setResultCount(10);
        word.setText("手机");
        word.setUseCount(100);
        //
        mongoOps.insert(word);
        //
        word = mongoOps.findOne(new Query(where(WordModel.TEXT_FIELD).is("手机")), WordModel.class);
        log.info(JsonKit.toJson(word));

        mongoOps.dropCollection("person");
    }
}
