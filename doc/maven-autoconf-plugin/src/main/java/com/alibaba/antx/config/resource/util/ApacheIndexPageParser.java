package com.alibaba.antx.config.resource.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

import com.alibaba.antx.config.resource.Resource;

public class ApacheIndexPageParser extends TextBasedPageParser {
    public ApacheIndexPageParser() {
        super();
    }

    public ApacheIndexPageParser(String overridingCharset) {
        super(overridingCharset);
    }

    public List parse(Resource resource) {
        Document doc = getHtmlDocument(resource);

        if (doc != null) {
            Node title = doc.selectSingleNode("//head/title");

            if (title != null && title.getText() != null && title.getText().startsWith("Index of")) {
                List nodes = doc
                        .selectNodes("//pre/img[starts-with(@alt,'[') and ends-with(@alt,']')]/following::a/@href");
                List items = new ArrayList(nodes.size());

                for (Iterator i = nodes.iterator(); i.hasNext();) {
                    Node node = (Node) i.next();
                    String name = node.getText();

                    try {
                        name = URLDecoder.decode(name, getCharset(resource));
                    } catch (UnsupportedEncodingException e) {
                    }

                    Item item = getItem(name);

                    if (item != null) {
                        items.add(item);
                    }
                }

                return items;
            }
        }

        return null;
    }
}
