package com.alibaba.antx.config.descriptor;

public class ConfigGenerate {
    private ConfigDescriptor descriptor;
    private String           template;
    private String           destfile;
    private String           charset;
    private String           outputCharset;

    public ConfigDescriptor getConfigDescriptor() {
        return descriptor;
    }

    public void setConfigDescriptor(ConfigDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getDestfile() {
        return destfile;
    }

    public void setDestfile(String destfile) {
        this.destfile = destfile;
    }

    public String getOutputCharset() {
        return outputCharset;
    }

    public void setOutputCharset(String outputCharset) {
        this.outputCharset = outputCharset;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String toString() {
        return "Generate[" + getTemplate() + " => " + getDestfile() + "]";
    }
}
