/**
 * Copyright 2013-2017 JueYue (qrb.jueyue@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.afterturn.gen.core.parse.impl;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cn.afterturn.gen.core.model.GenBeanEntity;
import cn.afterturn.gen.core.model.GenerationEntity;
import cn.afterturn.gen.core.parse.IParse;

/**
 *
 * @author JueYue
 * 2017年4月18日
 */
public class BeetlParseImplTest {

    @Test
    public void testParse() {
        IParse parse = new BeetlParseImpl();
        GenerationEntity generationEntity = new GenerationEntity();
        generationEntity.setEntityName("小明");
        GenBeanEntity tableEntity = new GenBeanEntity();
        tableEntity.setChinaName("绝月");
        List<String> fileList = new ArrayList<String>();
        fileList.add("hello,entity Name :${g.entityName}  ,chinaName: ${t.chinaName}");
        fileList = parse.parse(generationEntity, tableEntity, fileList);
        for (String string : fileList) {
            System.out.println(string);
        }
    }

}
