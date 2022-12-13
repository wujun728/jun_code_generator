package com.jeasy.mongodb;

import com.jeasy.common.json.JsonKit;
import com.jeasy.mongodb.dao.WordDao;
import com.jeasy.mongodb.model.WordModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * spring data mongodb测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/mongodb/spring-mongodb.xml")
public class MongoTest {

    /** */
    @Autowired
    private WordDao wordDao;

    /** */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * mongo插入
     */
    @Test
    public void insert() {
        try {
            WordModel word = new WordModel();
            word.setInitials("sj");
            word.setPinyin("shouji");
            word.setResultCount(10);
            word.setText("手机");
            word.setUseCount(100);
            //
            mongoTemplate.insert(word);
            //
            word = mongoTemplate.findOne(new Query(where(WordModel.TEXT_FIELD).is("手机")), WordModel.class);
            System.out.println("结果：" + JsonKit.toJson(word));

            List<WordModel> list = mongoTemplate.findAll(WordModel.class);
            System.out.println("结果List：" + JsonKit.toJson(list));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 分页查找
     */
    @Test
    public void findByPage() {
        Iterable<WordModel> it = wordDao.findAll();
        if (it != null && it.iterator() != null) {
            Iterator<WordModel> iterator = it.iterator();
            while (iterator.hasNext()) {
                System.out.println(JsonKit.toJson(iterator.next()));
            }
        }
    }
}
