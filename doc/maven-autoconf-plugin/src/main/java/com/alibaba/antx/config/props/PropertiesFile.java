package com.alibaba.antx.config.props;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.antx.config.generator.PropertiesLoader;
import com.alibaba.antx.config.resource.Resource;
import com.alibaba.antx.config.resource.ResourceManager;
import com.alibaba.antx.config.resource.ResourceURI;

/**
 * 代表一个props文件。
 * 
 * @author Michael Zhou
 */
public class PropertiesFile extends PropertiesResource {
    protected Set keys;
    protected Map props;

    protected PropertiesFile(ResourceManager manager) {
        super(manager);
    }

    public PropertiesFile(ResourceManager manager, String file) {
        super(manager);
        setURI(ResourceURI.guessURI(file));
    }

    public PropertiesFile(ResourceManager manager, File file) {
        super(manager);
        setURI(file);
    }

    public PropertiesFile(ResourceManager manager, URI uri) {
        super(manager);
        setURI(uri);
    }

    public PropertiesFile(ResourceManager manager, Resource res) {
        super(manager);
        setResource(res);
        setURI(res.getURI().getURI());
    }

    public Set getKeys() {
        load();
        return keys;
    }

    public Map getProperties() {
        load();
        return props;
    }

    protected void onLoad() {
        Map propsFromFile = PropertiesLoader.loadPropertiesFile(getResource().getInputStream(), getCharset(), getURI()
                .toString());

        keys = new TreeSet(propsFromFile.keySet());
        props = new HashMap();

        PropertiesLoader.mergeProperties(props, propsFromFile);
    }

    protected void onError() {
        keys = new TreeSet();
        props = new HashMap();
    }
}
