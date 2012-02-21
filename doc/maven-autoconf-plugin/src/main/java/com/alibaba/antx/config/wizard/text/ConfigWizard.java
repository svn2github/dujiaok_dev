package com.alibaba.antx.config.wizard.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.alibaba.antx.config.descriptor.ConfigDescriptor;
import com.alibaba.antx.config.descriptor.ConfigGroup;
import com.alibaba.antx.config.descriptor.ConfigProperty;
import com.alibaba.antx.config.descriptor.ConfigValidator;
import com.alibaba.antx.config.generator.expr.CompositeExpression;
import com.alibaba.antx.config.generator.expr.Expression;
import com.alibaba.antx.config.generator.expr.ExpressionContext;
import com.alibaba.antx.config.props.PropertiesSet;
import com.alibaba.antx.util.ObjectUtil;
import com.alibaba.antx.util.StringUtil;
import com.alibaba.antx.util.i18n.LocaleInfo;

/**
 * �����ı��Ľ��������������ļ��Ĺ����ࡣ
 * 
 * @author Michael Zhou
 */
public class ConfigWizard {
    private static final int PREVIOUS  = -1;
    private static final int NEXT      = -2;
    private static final int QUIT      = -3;
    private static final int MAX_ALIGN = 40;

    // wizard����
    private ConfigGroup[]    groups;
    private PropertiesSet    propSet;
    private String           confirmMessage;
    private BufferedReader   in;
    private PrintWriter      out;
    private PrintWriter      fileWriter;

    // Wizard״̬����
    private int              step;
    private ConfigGroup      group;
    private ConfigProperty[] props;
    private ConfigProperty   validatorProperty;
    private String           validatorMessage;
    private int              validatorIndex;

    /**
     * ����һ��wizard��
     * 
     * @param descriptors ���������ļ�
     * @param props �����ļ�
     */
    public ConfigWizard(ConfigDescriptor[] descriptors, PropertiesSet propSet) {
        this.propSet = propSet;

        // ��ʼ�����������
        try {
            in = new BufferedReader(new InputStreamReader(System.in, propSet.getUserPropertiesFile().getCharset()));
            out = new PrintWriter(new OutputStreamWriter(System.out, propSet.getUserPropertiesFile().getCharset()),
                    true);
        } catch (UnsupportedEncodingException e) {
            throw new ConfigWizardException(e);
        }

        // ȡ��descriptors�е�����groups
        List groups = new ArrayList();

        for (int i = 0; i < descriptors.length; i++) {
            ConfigGroup[] descriptorGroups = descriptors[i].getGroups();

            for (int j = 0; j < descriptorGroups.length; j++) {
                groups.add(descriptorGroups[j]);
            }
        }

        this.groups = (ConfigGroup[]) groups.toArray(new ConfigGroup[groups.size()]);

        // ���ó�ʼstep
        setStep(0);
    }

    /**
     * ����ȷ����Ϣ��
     * 
     * @param message ȷ����Ϣ
     */
    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    /**
     * ��֤�����ļ��Ƿ���������descriptor����Ҫ��
     * 
     * @return �������Ҫ���򷵻�true
     */
    public boolean validate() {
        for (int i = 0; i < groups.length; i++) {
            setStep(i);

            for (int j = 0; j < props.length; j++) {
                ConfigProperty prop = props[j];

                String value = evaluatePropertyValue(prop, false);

                for (Iterator k = prop.getValidators().iterator(); k.hasNext();) {
                    ConfigValidator validator = (ConfigValidator) k.next();

                    if (!validator.validate(value)) {
                        validatorIndex = j;
                        validatorProperty = prop;
                        validatorMessage = validator.getMessage();
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private String getURI() {
        return propSet.getUserPropertiesFile().getURI().toString();
    }

    private Set getKeys() {
        return propSet.getMergedKeys();
    }

    private Map getValues() {
        return propSet.getMergedProperties();
    }

    /**
     * ���Ĭ��ֵ��
     */
    private void fillDefaultValues() {
        int savedStep = step;

        for (int i = 0; i < groups.length; i++) {
            setStep(i);

            for (int j = 0; j < props.length; j++) {
                ConfigProperty prop = props[j];

                // ����key������value��Ϊ�գ���������Ĭ��ֵ
                if ((getValues().get(prop.getName()) == null) || !getKeys().contains(prop.getName())) {
                    String value = getPropertyValue(prop, true);

                    setProperty(prop.getName(), (value == null) ? "" : value);
                }
            }
        }

        setStep(savedStep);
    }

    /**
     * ִ��wizard��
     */
    public void start() {
        boolean continueWizard = true;

        // ���û��ʲô�����ģ�ֱ�ӷ���
        if (group == null) {
            confirmAndSave();
            return;
        }

        // ��ʾ�û�, �Ƿ����
        if (confirmMessage != null) {
            print(confirmMessage + " [Yes][No] ");

            try {
                String input = in.readLine();

                input = (input == null) ? "" : input.trim().toLowerCase();

                if (input.equals("n") || input.equals("no")) {
                    continueWizard = false;
                }
            } catch (IOException e) {
                throw new ConfigWizardException(e);
            }
        }

        println();

        // װ��Ĭ��ֵ���Լ��û��Ĳ���
        if (continueWizard) {
            fillDefaultValues();
        }

        // ��ʼwizard
        while (continueWizard) {
            // ��ӡ����
            printTitle();

            // ��ӡgroup����
            printGroup();

            // ��ʾ����
            int toStep = processMenu();

            switch (toStep) {
                case PREVIOUS:
                    setStep(step - 1);
                    break;

                case NEXT:
                    setStep(step + 1);
                    break;

                case QUIT:
                    continueWizard = confirmAndSave();
                    break;

                default:
                    processInput(toStep);
            }
        }
    }

    private boolean confirmAndSave() {
        boolean continueWizard = true;

        if (confirmSave()) {
            if (validateSave()) {
                save();
                continueWizard = false;
            }
        } else {
            // �û�ѡ���ˡ��˳������桱������Ҫ�ָ�ԭʼ������
            propSet.reloadUserProperties();
            continueWizard = false;
        }

        return continueWizard;
    }

    private void print(Object message) {
        String messageString = (message == null) ? "" : message.toString();

        out.print(messageString);
        out.flush();

        if (fileWriter != null) {
            fileWriter.print(messageString);
            fileWriter.flush();
        }
    }

    private void println(Object message) {
        String messageString = (message == null) ? "" : message.toString();

        out.println(((fileWriter == null) ? "" : "��") + messageString);

        if (fileWriter != null) {
            fileWriter.println(messageString);
        }
    }

    private void println() {
        println(null);
    }

    private void printTitle() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("�q�������������Щ� Step ").append(step + 1);
        buffer.append(" of ").append(groups.length).append(" ������������������������\n");

        buffer.append("��            ��\n");

        if (group.getConfigDescriptor().getDescription() != null) {
            buffer.append(
                    formatLines(group.getConfigDescriptor().getDescription(), 60, LocaleInfo.getDefault().getLocale(),
                            "��Description �� ", "��            ��   ")).append("\n");

            buffer.append("������������������������������������������������������������\n");
        }

        buffer.append(
                formatLines(group.getConfigDescriptor().getURL().toString(), 60, LocaleInfo.getDefault().getLocale(),
                        "��Descriptor  �� ", "��            ��   ")).append("\n");

        buffer.append("������������������������������������������������������������\n");

        buffer.append(
                formatLines(getURI(), 60, LocaleInfo.getDefault().getLocale(), "��Properties  �� ", "��            ��   "))
                .append("\n");

        buffer.append("��            ��").append("\n");
        buffer.append("���������������ة���������������������").append("\n");

        println();
        println(buffer);
    }

    private void printGroup() {
        if (group.getDescription() != null) {
            println(" " + group.getDescription() + " (? - ��ֵ���û������ļ��в����ڣ�* - �����S - ���ǹ���Ĭ��ֵ��s - ����ֵ)");
        } else {
            println(" (? - ��ֵ���û������ļ��в����ڣ�* - �����S - ���ǹ���Ĭ��ֵ��s - ����ֵ)");
        }

        println();

        // �ҳ�������ƺ�ֵ
        int maxLength = -1;
        int maxLengthValue = -1;

        for (int i = 0; i < props.length; i++) {
            int length = props[i].getName().length();

            if ((length > maxLength) && (length < MAX_ALIGN)) {
                maxLength = length;
            }
        }

        for (int i = 0; i < props.length; i++) {
            String value = getPropertyValue(props[i], true);
            int length = Math.max(props[i].getName().length(), maxLength)
                    + ((value == null) ? 0 : ("  = ".length() + value.length()));

            if ((length > maxLengthValue) && (length < (MAX_ALIGN * 2))) {
                maxLengthValue = length;
            }
        }

        for (int i = 0; i < props.length; i++) {
            ConfigProperty prop = props[i];

            StringBuffer buffer = new StringBuffer();

            // �����Ŀ�������ļ��в����ڣ�����ʾ?
            boolean absent = !getKeys().contains(prop.getName());

            // ����Ǳ�����, ����ʾ*��
            boolean required = !prop.isNullable();

            // �����Ŀ������shared properties�У�����ʾS
            boolean shared = propSet.isShared(prop.getName());

            if (shared) {
                if (absent) {
                    buffer.append("s");
                } else {
                    buffer.append("S");
                }
            } else {
                if (absent) {
                    buffer.append("?");
                } else {
                    buffer.append(" ");
                }
            }

            if (required) {
                buffer.append("* ");
            } else {
                buffer.append("  ");
            }

            // ��ʾproperty���
            buffer.append(i + 1).append(" - ");

            // ��ʾproperty����
            buffer.append(prop.getName());

            // ��ʾpropertyֵ
            String value = getPropertyValue(prop, true);

            if (value != null) {
                for (int j = 0; j < (maxLength - prop.getName().length()); j++) {
                    buffer.append(' ');
                }

                buffer.append("  = ").append(value);
            }

            // ��ʾproperty����
            if (prop.getDescription() != null) {
                int length = (value == null) ? prop.getName().length() : (Math.max(prop.getName().length(), maxLength)
                        + "  = ".length() + value.length());

                for (int j = 0; j < (maxLengthValue - length); j++) {
                    buffer.append(' ');
                }

                buffer.append("   # ").append(prop.getDescription());
            }

            // ���ֵ�Ǳ��ʽ����ͬʱ��ʾ���ʽ�ļ���ֵ
            String evaluatedValue = evaluatePropertyValue(prop, true);

            if ((evaluatedValue != null) && !ObjectUtil.equals(value, evaluatedValue)) {
                buffer.append("\n");

                for (int j = 0; j < maxLength; j++) {
                    buffer.append(' ');
                }

                buffer.append("          (").append(evaluatedValue).append(")");

                if (i < (props.length - 1)) {
                    buffer.append("\n");
                }
            }

            println(buffer);
        }

        println();
    }

    private int processMenu() {
        while (true) {
            // ��ʾ����
            StringBuffer buffer = new StringBuffer(" ��ѡ��");

            if (props.length > 0) {
                buffer.append("[1-").append(props.length).append("]");
            }

            buffer.append("[Quit]");

            if (step > 0) {
                buffer.append("[Previous]");
            }

            if (step < (groups.length - 1)) {
                buffer.append("[Next]");
            }

            buffer.append(" ");

            print(buffer);

            // �ȴ�����
            String input = null;

            try {
                input = in.readLine();
            } catch (IOException e) {
                throw new ConfigWizardException(e);
            }

            input = (input == null) ? "" : input.trim().toLowerCase();

            if ((input.equals("n") || input.equals("next")) && (step < (groups.length - 1))) {
                return NEXT;
            }

            if ((input.equals("p") || input.equals("previous")) && (step > 0)) {
                return PREVIOUS;
            }

            if (input.equals("q") || input.equals("quit")) {
                return QUIT;
            }

            try {
                int inputValue = Integer.parseInt(input) - 1;

                if ((inputValue >= 0) && (inputValue < props.length)) {
                    return inputValue;
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    private void processInput(int index) {
        ConfigProperty prop = props[index];
        StringBuffer buffer = new StringBuffer(" ������");

        // ��ʾproperty����
        if (prop.getDescription() != null) {
            buffer.append(prop.getDescription()).append(" ");
        }

        // ��ʾproperty����
        buffer.append(prop.getName()).append(" = ");

        // ��ʾpropertyֵ
        String value = getPropertyValue(prop, true);

        if (value != null) {
            buffer.append("[").append(value).append("] ");
        }

        print(buffer);

        // �ȴ�����
        String input = null;

        try {
            input = in.readLine();
        } catch (IOException e) {
            throw new ConfigWizardException(e);
        }

        input = (input == null) ? "" : input.trim();

        if ((input == null) || (input.length() == 0)) {
            input = value;
        }

        setProperty(prop.getName(), input);
    }

    private boolean confirmSave() {
        println();
        print(" �������浽�ļ�\"" + getURI() + "\"��, ȷ��? [Yes][No] ");

        String input = null;

        try {
            input = in.readLine();
        } catch (IOException e) {
            throw new ConfigWizardException(e);
        }

        input = (input == null) ? "" : input.trim().toLowerCase();

        if (input.equals("n") || input.equals("no")) {
            return false;
        }

        return true;
    }

    private boolean validateSave() {
        if (!validate()) {
            println();
            println(" �ֶ�" + validatorProperty.getName() + "���Ϸ�: " + validatorMessage);
            println();
            print(" ����ȻҪ������? [Yes=ǿ�Ʊ���/No=�����༭] ");

            String input = null;

            try {
                input = in.readLine();
            } catch (IOException e) {
                throw new ConfigWizardException(e);
            }

            input = (input == null) ? "" : input.trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            }

            printTitle();
            printGroup();
            processInput(validatorIndex);

            return false;
        }

        return true;
    }

    private void save() {
        Map modifiedProperties = propSet.getModifiedProperties();

        println();
        println("�q������������������������������������������������������");
        println("�� �����ļ� " + getURI() + "...");
        println("������������������������������������������������������������");

        try {
            OutputStream os = propSet.getUserPropertiesFile().getResource().getOutputStream();
            String charset = propSet.getUserPropertiesFile().getCharset();

            fileWriter = new PrintWriter(new OutputStreamWriter(os, charset), true);
        } catch (IOException e) {
            throw new ConfigWizardException(e);
        }

        try {
            List[] keyGroups = getSortedKeys(modifiedProperties, 2);

            for (int i = 0; i < keyGroups.length; i++) {
                List keys = keyGroups[i];

                // �ҳ��������
                int maxLength = -1;

                for (Iterator j = keys.iterator(); j.hasNext();) {
                    String key = (String) j.next();
                    int length = key.length();

                    if ((length > maxLength) && (length < MAX_ALIGN)) {
                        maxLength = length;
                    }
                }

                // ���property��
                for (Iterator j = keys.iterator(); j.hasNext();) {
                    String key = (String) j.next();
                    String value = (String) modifiedProperties.get(key);

                    if (value == null) {
                        value = "";
                    }

                    value = ((String) value).replaceAll("\\\\", "\\\\\\\\");

                    StringBuffer buffer = new StringBuffer();

                    buffer.append(key);

                    for (int k = 0; k < (maxLength - key.length()); k++) {
                        buffer.append(' ');
                    }

                    buffer.append("  = ").append(value);

                    println(buffer);
                }

                if (i < (keyGroups.length - 1)) {
                    println();
                }
            }
        } finally {
            fileWriter.close();
            fileWriter = null;
        }

        println("��������������������������������������");
        println(" �ѱ������ļ�: " + getURI());

        propSet.reloadUserProperties();
    }

    /**
     * ������properties��key���򲢷��顣
     * 
     * @param level ����ļ���
     * @return �����б�
     */
    private List[] getSortedKeys(Map props, int level) {
        List keys = new ArrayList(props.keySet());

        Collections.sort(keys);

        List groups = new ArrayList();
        List group = null;
        String prefix = null;

        for (Iterator i = keys.iterator(); i.hasNext();) {
            String key = (String) i.next();
            String[] parts = StringUtil.split(key, ".");
            StringBuffer buffer = new StringBuffer();

            for (int j = 0; (j < (parts.length - 1)) && (j < level); j++) {
                if (buffer.length() > 0) {
                    buffer.append('.');
                }

                buffer.append(parts[j]);
            }

            String keyPrefix = buffer.toString();

            if (!keyPrefix.equals(prefix)) {
                if (group != null) {
                    groups.add(group);
                }

                prefix = keyPrefix;
                group = new ArrayList();
            }

            group.add(key);
        }

        if ((group != null) && (group.size() > 0)) {
            groups.add(group);
        }

        return (List[]) groups.toArray(new List[groups.size()]);
    }

    private void setProperty(String name, String value) {
        Object expr = value;

        if (value != null) {
            expr = CompositeExpression.parse(value);
        }

        if (expr == null) {
            getValues().remove(name);
            getKeys().remove(name);
        } else {
            getValues().put(name, expr);
            getValues().put(StringUtil.getValidIdentifier(name), expr);
            getKeys().add(name);
        }
    }

    /**
     * ȡ��property��ֵ����������ʽ��
     * 
     * @param prop ����
     * @param defaultValue �Ƿ�ʹ��Ĭ��ֵ
     */
    private String getPropertyValue(ConfigProperty prop, boolean defaultValue) {
        Object value = getValues().get(prop.getName());

        if (defaultValue && (value == null)) {
            value = prop.getDefaultValue();
        }

        if (value instanceof Expression) {
            value = ((Expression) value).getExpressionText();
        }

        if (value instanceof String) {
            String stringValue = (String) value;

            if (stringValue != null) {
                stringValue = stringValue.trim();
            }

            if ((stringValue == null) || (stringValue.length() == 0)) {
                stringValue = null;
            }

            return stringValue;
        }

        return (value == null) ? null : value.toString();
    }

    /**
     * ����property��ֵ��
     * 
     * @param prop ����
     * @param defaultValue �Ƿ�ʹ��Ĭ��ֵ
     */
    private String evaluatePropertyValue(ConfigProperty prop, boolean defaultValue) {
        final String ref = prop.getName();
        Object value = getValues().get(ref);

        if (defaultValue && (value == null)) {
            value = prop.getDefaultValue();

            if (value instanceof String) {
                Expression expr = CompositeExpression.parse((String) value);

                if (expr != null) {
                    value = expr;
                }
            }
        }

        if (value instanceof Expression) {
            value = ((Expression) value).evaluate(new ExpressionContext() {
                public Object get(String key) {
                    // �������޵ݹ�
                    if (ref.equals(key)
                            || StringUtil.getValidIdentifier(ref).equals(StringUtil.getValidIdentifier(key))) {
                        return null;
                    } else {
                        return getValues().get(key);
                    }
                }

                public void put(String key, Object value) {
                    getValues().put(key, value);
                }
            });
        }

        if (value instanceof String) {
            String stringValue = (String) value;

            if (stringValue != null) {
                stringValue = stringValue.trim();
            }

            if ((stringValue == null) || (stringValue.length() == 0)) {
                stringValue = null;
            }

            return stringValue;
        }

        return (value == null) ? null : value.toString();
    }

    /**
     * ���õ�ǰ������
     * 
     * @param step ��ǰ����
     */
    private void setStep(int step) {
        if (step >= groups.length) {
            step = groups.length - 1;
        }

        if (step < 0) {
            step = 0;
        }

        this.step = step;

        if (step < groups.length) {
            this.group = groups[step];
            this.props = group.getProperties();
        } else {
            this.group = null;
        }
    }

    /**
     * ��ʽ���ַ���������ַ�������ָ�����ȣ����Զ����С�
     * 
     * @param text Ҫ��ʽ�����ַ���
     * @param maxLength �еĳ���
     * @param locale ���ҵ���
     * @param prefix1 ����ǰ׺
     * @param prefix2 �ڶ��м������е�ǰ׺
     * @return ��ʽ������ַ���
     */
    private String formatLines(String text, int maxLength, Locale locale, String prefix1, String prefix) {
        BreakIterator boundary = BreakIterator.getLineInstance(locale);
        StringBuffer result = new StringBuffer(prefix1);

        boundary.setText(text);

        int start = boundary.first();
        int end = boundary.next();
        int lineLength = 0;

        while (end != BreakIterator.DONE) {
            String word = text.substring(start, end);

            lineLength = lineLength + word.length();

            if (lineLength >= maxLength) {
                result.append("\n").append(prefix);
                lineLength = word.length();
            }

            result.append(word);
            start = end;
            end = boundary.next();
        }

        return result.toString();
    }
}
