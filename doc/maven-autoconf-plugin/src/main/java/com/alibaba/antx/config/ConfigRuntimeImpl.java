package com.alibaba.antx.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.antx.config.descriptor.ConfigDescriptor;
import com.alibaba.antx.config.entry.ConfigEntry;
import com.alibaba.antx.config.entry.ConfigEntryFactory;
import com.alibaba.antx.config.entry.ConfigEntryFactoryImpl;
import com.alibaba.antx.config.props.PropertiesResource;
import com.alibaba.antx.config.props.PropertiesSet;
import com.alibaba.antx.config.wizard.text.ConfigWizardLoader;
import com.alibaba.antx.util.PatternSet;
import com.alibaba.antx.util.StringUtil;

public class ConfigRuntimeImpl implements ConfigRuntime {
    private BufferedReader     in;
    private PrintWriter        out;
    private PrintWriter        err;
    private String             charset;
    private String             mode;
    private String             interactiveMode;
    private PatternSet         descriptorPatterns;
    private PatternSet         packagePatterns;
    private String[]           dests;
    private File[]             destFiles;
    private PropertiesSet      props;
    private boolean            verbose;
    private File               tempdir;
    private ConfigEntryFactory configEntryFactory = new ConfigEntryFactoryImpl(this);

    public ConfigRuntimeImpl() {
        this(System.in, System.out, System.err, null);
    }

    public ConfigRuntimeImpl(InputStream inputStream, OutputStream outStream, OutputStream errStream, String charset) {
        this.interactiveMode = ConfigConstant.INTERACTIVE_AUTO;
        this.charset = StringUtil.isEmpty(charset) ? ConfigConstant.DEFAULT_CHARSET : charset;

        try {
            in = new BufferedReader(new InputStreamReader(inputStream, this.charset));
            out = new PrintWriter(new OutputStreamWriter(outStream, this.charset), true);
            err = new PrintWriter(new OutputStreamWriter(errStream, this.charset), true);
        } catch (UnsupportedEncodingException e) {
            throw new ConfigException(e); // 不应发生
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public PrintWriter getErr() {
        return err;
    }

    public String getCharset() {
        return charset;
    }

    public PatternSet getDescriptorPatterns() {
        return descriptorPatterns;
    }

    public PatternSet getPackagePatterns() {
        return packagePatterns;
    }

    public String getInteractiveMode() {
        return interactiveMode;
    }

    public String getMode() {
        return mode;
    }

    public File[] getDestFiles() {
        return destFiles;
    }

    public PropertiesSet getPropertiesSet() {
        if (props == null) {
            props = new PropertiesSet(getIn(), getOut());
        }

        return props;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public ConfigEntryFactory getConfigEntryFactory() {
        return configEntryFactory;
    }

    public void setDescriptorPatterns(String includes, String excludes) {
        this.descriptorPatterns = new PatternSet(includes, excludes);
    }

    public void setDescriptorPatterns(String[] includes, String[] excludes) {
        this.descriptorPatterns = new PatternSet(includes, excludes);
    }

    public void setPackagePatterns(String includes, String excludes) {
        this.packagePatterns = new PatternSet(includes, excludes);
    }

    public void setPackagePatterns(String[] includes, String[] excludes) {
        this.packagePatterns = new PatternSet(includes, excludes);
    }

    public void setInteractiveMode(String mode) {
        if (ConfigConstant.INTERACTIVE_AUTO.equals(mode) || ConfigConstant.INTERACTIVE_ON.equals(mode)
                || ConfigConstant.INTERACTIVE_OFF.equals(mode)) {
            this.interactiveMode = mode;
        }
    }

    public void setGuiMode() {
        mode = ConfigConstant.MODE_GUI;
    }

    public void setTextMode() {
        mode = ConfigConstant.MODE_TEXT;
    }

    public void setDests(String[] dests) {
        this.dests = dests;
    }

    public void setDestFiles(File[] destFiles) {
        this.destFiles = (File[]) destFiles.clone();
    }

    public void setUserPropertiesFile(String userPropertiesFile, String charset) {
        PropertiesSet props = getPropertiesSet();

        props.setUserPropertiesFile(userPropertiesFile);
        props.getUserPropertiesFile().setCharset(charset);
    }

    public void setSharedPropertiesFiles(String[] sharedPropertiesFiles, String name, String charset) {
        getPropertiesSet().setSharedPropertiesFiles(sharedPropertiesFiles);
        getPropertiesSet().setSharedPropertiesFilesName(name);

        PropertiesResource[] resources = getPropertiesSet().getSharedPropertiesFiles();

        for (int i = 0; i < resources.length; i++) {
            resources[i].setCharset(charset);
        }

        if (!StringUtil.isEmpty(name) || (sharedPropertiesFiles != null && sharedPropertiesFiles.length > 0)) {
            this.interactiveMode = ConfigConstant.INTERACTIVE_ON;
        }
    }

    public void setVerbose() {
        this.verbose = true;
    }

    private void init() {
        // tempdir
        if (tempdir == null) {
            tempdir = new File("");
        }

        tempdir = tempdir.getAbsoluteFile();

        // dests
        dests = StringUtil.trimStringArray(dests);

        if (dests.length > 0) {
            destFiles = new File[dests.length];

            for (int i = 0; i < dests.length; i++) {
                destFiles[i] = new File(dests[i]).getAbsoluteFile();
            }
        } else {
            destFiles = new File[0];
        }

        // user properties file
        getPropertiesSet().init();

        info("User-defined properties: " + getPropertiesSet().getUserPropertiesFile().getURI() + "\n");
    }

    public void debug(String message) {
        if (verbose) {
            getOut().println(message);
        }
    }

    public void info(String message) {
        getOut().println(message);
    }

    public void warn(String message) {
        getOut().println(message);
    }

    public void error(String message) {
        error(message, null);
    }

    public void error(Throwable cause) {
        error(null, cause);
    }

    public void error(String message, Throwable cause) {
        if (StringUtil.isBlank(message) && (cause != null)) {
            message = "ERROR: " + cause.getMessage();
        }

        getErr().println(message);

        if (verbose) {
            cause.printStackTrace(getErr());
            getErr().println();
        }
    }

    public void start() {
        start(null);
    }

    public void start(ConfigDescriptor inlineDescriptor) {
        init();

        if (inlineDescriptor == null && ConfigConstant.MODE_GUI.equals(mode)) {
            //MainWindow.run(this);
        } else if (inlineDescriptor == null) {
            // 扫描所有文件或目录，取得要配置的entries
            List<ConfigEntry> entries = scan(false);

            if (entries.isEmpty() && !ConfigConstant.INTERACTIVE_ON.equals(interactiveMode)) {
                info("Nothing to configure");
                return;
            }

            ConfigWizardLoader wizard = new ConfigWizardLoader(this, entries);

            // 交互式编辑props文件
            wizard.loadAndStart();

            // 生成配置文件
            for (Iterator<ConfigEntry> i = entries.iterator(); i.hasNext();) {
                ConfigEntry entry = (ConfigEntry) i.next();

                entry.generate();
            }
        } else {
            ConfigWizardLoader wizard = new ConfigWizardLoader(this, inlineDescriptor);

            // 交互式编辑props文件
            wizard.loadAndStart();
        }
    }

    public List<ConfigEntry> scan(boolean includeEmptyEntries) {
        List<ConfigEntry> entries = new ArrayList<ConfigEntry>(destFiles.length);

        for (int i = 0; i < destFiles.length; i++) {
            File destFile = destFiles[i];
            ConfigEntry entry = getConfigEntryFactory().create(new ConfigResource(destFile));

            entry.scan();

            if (includeEmptyEntries || !entry.isEmpty()) {
                entries.add(entry);
            }
        }

        return entries;
    }
}
