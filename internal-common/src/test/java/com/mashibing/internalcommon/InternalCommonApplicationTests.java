package com.mashibing.internalcommon;

import io.vavr.Tuple;
import io.vavr.Tuple4;
import io.vavr.control.Try;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class InternalCommonApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * https://blog.csdn.net/fenglllle/article/details/81389286
     */
    @Test
    public void testStack() {
        String s = "第一次新建，第二次修改，\n" +
                "第三次修改";
        System.out.println(s.replaceAll("\n|\t|\r", ""));
        Man man = new Man();
        man.setName("test");
        System.out.println(man);
        handleAttrByMan(man);
        System.out.println(man.getName());
    }

    public static void handleAttrByMan(Man man) {
        man.setName("test111");
    }

    class Man{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Man{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testParseXml() {
        try {
            File xmlFile = new File("C:\\Users\\Kevin\\Desktop\\CbsbeeE007002_20210510_CBTFN0.xml");
            // 3.解析xml文件
            // 3.1过滤文件的Unicode字符
            String sXML = filterUnicode(xmlFile, "UTF-8");
            Document document = DocumentHelper.parseText(sXML);
            Element rootElement = document.getRootElement();

            // 3.2主要节点抽取: rooeEle有最多有三个节点，Envelop、Items、Relations
            // ROOT: Envelop    ROOT:Items/Item/MetaInfo
            Element Envelop = rootElement.element("Envelop");
            Element Items = rootElement.element("Items");
            Element Item = Items.element("Item");
            // 节点Item下有两个节点MetaInfo和Contents
            Element MetaInfo = Item.element("MetaInfo");

            // 3.5 DescriptionMetaGroup标签,抽取稿件信息
            Element DescriptionMetaGroup = MetaInfo.element("DescriptionMetaGroup");
            Tuple4<String, String, LinkedList<String>, String> tuple4 = abstractAttrsByDescriptionMetaGroupTag(DescriptionMetaGroup);

            System.out.println( Try.of(tuple4._3()::getLast).getOrNull());
            System.out.println(isChinese(tuple4._1));
            Object o = new Object();
            xxxx(o);
            System.out.println(Objects.isNull(o));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void xxxx(Object o) {
        o = null;
    }

    public static boolean isChinese(String str) {

        String regEx = "[\\u4e00-\\u9fa5]+";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        return m.find();

    }

    private Tuple4<String, String, LinkedList<String>, String> abstractAttrsByDescriptionMetaGroupTag(Element descMetaGroupTag) {
        // 1.标题
        Element Titles = descMetaGroupTag.element("Titles");
        Element HeadLine = Titles.element("HeadLine");
        String sTitle = HeadLine.getText();

        // 2.作者 作者字段可能丢失，所以做个特殊处理
        String sAuthor = "";
        try {
            Element Creators = descMetaGroupTag.element("Creators");
            Element Creator = Creators.element("Creator");
            Element creatorName = Creator.element("Name");
            Element FullName = creatorName.element("FullName");
            sAuthor = FullName.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3.稿件分类
        Element SubjectCodes = descMetaGroupTag.element("SubjectCodes");
        List<?> SubjectCodeList = SubjectCodes.elements("SubjectCode");
        LinkedList<String> docClassifyList = new LinkedList<>();
        for (Iterator<?> it = SubjectCodeList.iterator(); it.hasNext(); ) {
            Element SubjectCode = (Element) it.next();
            Element MainCode = SubjectCode.element("MainCode");
            Element DocClassify = MainCode.element("Name");
            docClassifyList.add(DocClassify.getText());
        }

        // 关键词
        Element Keywords = descMetaGroupTag.element("Keywords");
        String sKeyword = "";
        if (Keywords != null) {
            Element Keyword = Keywords.element("Keyword");
            sKeyword = Keyword.getText();
        }
        return Tuple.of(sTitle, sAuthor, docClassifyList, sKeyword);
    }


    public static String filterUnicode(File _file, String _encoding) throws Exception {
        if (!_file.exists()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        java.io.BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    _file), _encoding));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(UnicodeStringHandler(str)).append('\r').append('\n');
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (br != null)
                br.close();
        }
        return sb.toString();
    }

    /**
     * 对指定的参数内容，如果存在Unicode字符，则替换为空格
     *
     * @param value
     * @return
     */
    private static String UnicodeStringHandler(String value) {
        if (value == null)
            return null;

        char[] chs = value.toCharArray();

        for (int i = 0; i < value.length(); ++i) {
            if (chs[i] > 0xFFFD) {
                chs[i] = ' ';
            } else if (chs[i] < 0x20 && chs[i] != '\t' & chs[i] != '\n'
                    & chs[i] != '\r') {
                chs[i] = ' ';
            } else if (chs[i] >= 0x80 && chs[i] <= 0x9f) {
                chs[i] = ' ';
            }
        }
        return new String(chs);
    }
}
