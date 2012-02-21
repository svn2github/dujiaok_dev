package com.alibaba.antx.config.resource.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Node;

import com.alibaba.antx.config.resource.Resource;

public class SvnIndexPageParser extends TextBasedPageParser {
    public List parse(Resource resource) {
        Document doc;
        boolean xml = true;

        doc = getXmlDocument(resource);

        if (doc == null) {
            doc = getHtmlDocument(resource);
            xml = false;
        }

        List items = null;

        if (doc != null && xml) {
            DocumentType docType = doc.getDocType();

            if (docType != null && "svn".equalsIgnoreCase(docType.getName())) {
                items = new ArrayList();

                addNodes(items, doc.selectNodes("//dir/@name"), true);
                addNodes(items, doc.selectNodes("//file/@name"), false);
            }
        } else if (doc != null && !xml) {
            Node title = doc.selectSingleNode("//head/title");

            if (title != null && title.getText() != null && title.getText().indexOf("Revision") > 0) {
                List nodes = doc.selectNodes("//ul/li/a/@href");

                items = new ArrayList();

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
            }
        }

        return items;
    }

    private void addNodes(List items, List listOfNodes, boolean directory) {
        for (Iterator i = listOfNodes.iterator(); i.hasNext();) {
            Node node = (Node) i.next();

            items.add(new Item(node.getText(), directory));
        }
    }
}
