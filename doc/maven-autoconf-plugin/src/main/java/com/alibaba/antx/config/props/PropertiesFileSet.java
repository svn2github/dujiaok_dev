package com.alibaba.antx.config.props;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.antx.config.resource.Resource;
import com.alibaba.antx.config.resource.ResourceManager;
import com.alibaba.antx.config.resource.ResourceURI;

/**
 * 代表一组props文件。
 * 
 * @author Michael Zhou
 */
public class PropertiesFileSet extends PropertiesResource {
    private List files = new ArrayList();

    public PropertiesFileSet(ResourceManager manager, String file) {
        super(manager);
        setURI(ResourceURI.guessURI(file));
    }

    public PropertiesFileSet(ResourceManager manager, File file) {
        super(manager);
        setURI(file);
    }

    public PropertiesFileSet(ResourceManager manager, URI url) {
        super(manager);
        setURI(url);
    }

    public List getPropertiesFiles() {
        load();
        return files;
    }

    protected void onLoad() {
        List subresources = getResource().list();

        for (Iterator i = subresources.iterator(); i.hasNext();) {
            Resource subres = (Resource) i.next();

            if (!subres.isDirectory()) {
                PropertiesFile file = new PropertiesFile(manager, subres);

                files.add(file);
            }
        }
    }
}
