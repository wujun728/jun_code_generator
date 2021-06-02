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
package cn.afterturn.gen.core.parse;

import cn.afterturn.gen.core.db.exception.GenerationRunTimeException;
import cn.afterturn.gen.core.parse.impl.BeetlParseImpl;
import cn.afterturn.gen.core.parse.impl.FreeMarkerParseImpl;

import static cn.afterturn.gen.core.GenCoreConstant.BEETL;
import static cn.afterturn.gen.core.GenCoreConstant.FREEMARKER;

/**
 *
 * @author JueYue
 * 2017年4月18日
 */
public class ParseFactory {

    public static IParse getParse(String parseType) {
        if (BEETL.equalsIgnoreCase(parseType)) {
            return new BeetlParseImpl();
        }
        if (FREEMARKER.equalsIgnoreCase(parseType)) {
            return new FreeMarkerParseImpl();
        }
        throw new GenerationRunTimeException("模板类型不支持");
    }

}
