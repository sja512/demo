package com.sja.demo.fenci;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author sja  created on 2019/5/13.
 */
public class Fenci {

    public static void main(String[] args) throws IOException {
        String[] areaArray = {"锡林郭勒盟","鄂尔多斯市","上海市", "江苏省", "内蒙古自治区","宁夏回族自治区","海西蒙古族藏族自治州","澳门特别行政区"};
        for (String area : areaArray) {
            fenci2(area);

        }

    }

    public static void fenci2(String conten) throws IOException {
        StringReader sr = new StringReader(conten);
        IKSegmenter ik = new IKSegmenter(sr, false);
        Lexeme lex = null;
        while ((lex = ik.next()) != null) {
            System.out.println(lex.getLexemeText());
        }
    }


    public static void fenci1() throws IOException {
        String text = "你好，我的世界!";
        //创建分词对象
        Analyzer anal = new IKAnalyzer(true);
        StringReader reader = new StringReader(text);
        //分词
        TokenStream ts = anal.tokenStream("content", reader);
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        while (ts.incrementToken()) {
            System.out.println(term.toString());
        }
        reader.close();
    }
}
