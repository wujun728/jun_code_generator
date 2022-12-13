package com.jeasy.common.dom;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.io.IoKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public final class DomKit {

    private DomKit() {
    }

    /**
     * 创建docment
     * @param root
     * @return
     */
    public static Document createDocument(String root) {
        try {
            return DocumentHelper.createDocument(DocumentHelper.createElement(root));
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 将xml类型的字符串转换成document对象进行操作
     * @param xml
     * @return
     */
    public static Document parseStringToXml(String xml) {
        try {
            return DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            throw new KitException(e);
        }
    }

    /**
     * 读取xml文件
     * @param filePath
     * @return
     */
    public static Document parseFileToXml(String filePath) {
        SAXReader saxReader = new SAXReader();
        Document doc;
        try {
            doc = saxReader.read(new File(filePath));
            return doc;
        } catch (DocumentException e) {
            throw new KitException(e);
        }
    }

    /**
     * 获取ClassPath相对路径下的xml文件
     * @param filePath
     * @return
     */
    public static Document parseFIOToXml(String filePath) {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            InputStream in = DomKit.class.getClassLoader().getResourceAsStream(filePath);
            doc = saxReader.read(in);
            return doc;
        } catch (DocumentException e) {
            throw new KitException(e);
        }
    }

    /**
     * 根据xpath 获取指定的元素
     * @param xpath
     * @param doc
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Element getElementByXpath(String xpath, Document doc) {
        List<Element> parameterList = doc.selectNodes(xpath);
        if (parameterList != null && parameterList.size() > 0) {
            return parameterList.get(0);
        }
        return null;
    }

    /**
     * 获取指定id的xml元素(单个xml中id唯一的情况下使用)
     * @param id
     * @param doc
     * @return
     */
    public static Element getElementById(String id, Document doc) {
        return getElementByXpath("//*[@id='" + id + "']", doc);
    }

    /**
     * 插入元素
     * @param name
     * @param value
     * @param doc
     * @return
     */
    public static void insertElement(String name, String value, Document doc) {
        insertElement(name, value, doc, false);
    }

    /**
     * 插入元素
     * @param name
     * @param value
     * @param doc
     * @return
     */
    public static void insertElement(String name, String value, Document doc, boolean isCdata) {
        Element ele = DocumentHelper.createElement(name);
        if (isCdata) {
            CDATA cdata = DocumentHelper.createCDATA(value);
            ele.add(cdata);
        } else {
            ele.setText(value);
        }
        doc.getRootElement().add(ele);
    }

    /**
     * 在指定id的元素后面插入元素
     * @param pid
     * @param newele
     * @param doc
     * @return
     */
    public static Document insertElement(String pid, Element newele, Document doc) {
        return insertElement(pid, newele, doc, 1);
    }

    /**
     * 在指定id的元素后面或前面插入元素
     * @param pid
     * @param newele
     * @param doc
     * @param i 为1表示在坐标元素之后----- 为0 则在坐标元素之前
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Document insertElement(String pid, Element newele, Document doc, int i) {
        // 坐标元素
        Element element = getElementById(pid, doc);

        // 获取坐标元素父元素下的所有元素
        List<Element> list = element.getParent().content();

        list.add(list.indexOf(element) + i, newele);

        return doc;
    }

    /**
     * 删除文档doc的指定路径下的所有子节点（包含元素，属性等） <br/>
     * 如果路径相同一并删除
     * @param doc 文档对象
     * @param xpath 指定元素的路径 根据路径可删除元素、属性
     * @return 删除成功时返回true，否则false
     */
    @SuppressWarnings("unchecked")
    public static boolean deleteNodes(Document doc, String xpath) {
        boolean flag = true;
        try {
            List<Node> nlist = doc.selectNodes(xpath);
            for (Node node : nlist) {
                node.detach();
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 删除一个父元素下所有的子节点（包含元素，属性等）
     * @param element 父元素
     * @return 删除成功时返回true，否则false
     */
    @SuppressWarnings("unchecked")
    public static boolean deleteChildren(Element element) {
        boolean flag = true;
        try {
            List<Node> nlist = element.elements();
            for (Node node : nlist) {
                node.detach();
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 删除指定的元素
     * @param ele
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean deleteElement(Element ele) {
        List<Element> list = ele.getParent().content();
        list.remove(list.indexOf(ele));
        return true;
    }

    /**
     * 将doc转为字符串
     * @param doc
     * @return
     */
    public static String docToString(Document doc) {
        return doc.asXML();
    }

    /**
     * 保存xml
     * @param filePath
     * @param document
     * @return 操作标识符
     */
    public static boolean saveDocument(String filePath, Document document) {
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(filePath), CharsetKit.DEFAULT_ENCODE));
            writer.write(document);
            return true;
        } catch (IOException e) {
            throw new KitException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                throw new KitException(e);
            }
        }
    }

    /**
     * 对象转XML
     * @param className
     * @param object
     * @return
     */
    public static String toXml(Class<?> className, Object object) {
        String strXml = StrKit.S_EMPTY;
        StringWriter writer = null;
        try {
            writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(className);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, writer);
            strXml = writer.toString();
            writer.flush();

            strXml = strXml.replace("&lt;", "<");
            strXml = strXml.replace("&gt;", ">");
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(writer);
        }
        return strXml;
    }

    /**
     * XML转对象
     * @param className
     * @param strXml
     * @return
     */
    public static Object toObject(Class<?> className, String strXml) {
        Object object = null;
        StringReader reader = null;
        try {
            reader = new StringReader(strXml);
            JAXBContext context = JAXBContext.newInstance(className);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            object = unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader);
        }
        return object;
    }
}
