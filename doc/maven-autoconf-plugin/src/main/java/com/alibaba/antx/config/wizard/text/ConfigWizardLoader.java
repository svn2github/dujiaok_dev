package com.alibaba.antx.config.wizard.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.antx.config.ConfigConstant;
import com.alibaba.antx.config.ConfigSettings;
import com.alibaba.antx.config.descriptor.ConfigDescriptor;
import com.alibaba.antx.config.entry.ConfigEntry;
import com.alibaba.antx.config.props.PropertiesSet;

public class ConfigWizardLoader {
    private final ConfigSettings   settings;
    private final List             configEntries;
    private final ConfigDescriptor inlineDescriptor;

    public ConfigWizardLoader(ConfigSettings settings, List configEntries) {
        this.settings = settings;
        this.configEntries = configEntries;
        this.inlineDescriptor = null;
    }

    public ConfigWizardLoader(ConfigSettings settings, ConfigDescriptor inlineDescription) {
        this.settings = settings;
        this.configEntries = null;
        this.inlineDescriptor = inlineDescription;
    }

    public void loadAndStart() {
        // ʹ��wizard��֤�����û�����
        ConfigDescriptor[] descriptors = getAllDescriptors();
        PropertiesSet props = settings.getPropertiesSet();
        ConfigWizard wizard = new ConfigWizard(descriptors, props);
        boolean valid = wizard.validate();
        String interactiveMode = settings.getInteractiveMode();
        boolean interactiveAuto = ConfigConstant.INTERACTIVE_AUTO.equals(interactiveMode);
        boolean interactiveOn = ConfigConstant.INTERACTIVE_ON.equals(interactiveMode);

        if (interactiveOn || (interactiveAuto && !valid)) {
            if (!valid) {
                StringBuffer confirm = new StringBuffer();

                confirm.append("�q������������������������������������������������������\n");
                confirm.append("��\n");
                confirm.append("�� ���������ļ���Ҫ�����£�\n");
                confirm.append("��\n");
                confirm.append("�� ").append(props.getUserPropertiesFile().getURI()).append("\n");
                confirm.append("��\n");
                confirm.append("�� ����ļ������������˵��������ã�\n");
                confirm.append("�� �����������˿ڡ������ʼ���ַ�����ݡ�\n");
                confirm.append("��\n");
                confirm.append("��������������������������������������\n");
                confirm.append("\n").append(" ��������´��ļ������ܻᵼ�������ļ������ݲ�������\n");
                confirm.append(" ����Ҫ���ڸ��´��ļ���?");

                wizard.setConfirmMessage(confirm.toString());
            }

            wizard.start();

            valid = wizard.validate();
        }

        // ����valid����Ϊtrue�����׳��쳣
        if (!valid) {
            throw new ConfigWizardException("��Ϊ�����ļ���" + props.getUserPropertiesFile().getURI() + "��δ׼���ã������޷�������ȥ��");
        }
    }

    /**
     * ȡ�����е�descriptors��
     * 
     * @return ����descriptors������
     */
    private ConfigDescriptor[] getAllDescriptors() {
        if (configEntries != null) {
            List descriptors = new ArrayList();

            for (Iterator i = configEntries.iterator(); i.hasNext();) {
                ConfigEntry entry = (ConfigEntry) i.next();

                addConfigEntryRecursive(entry, descriptors);
            }

            return (ConfigDescriptor[]) descriptors.toArray(new ConfigDescriptor[descriptors.size()]);
        } else if (inlineDescriptor != null) {
            return new ConfigDescriptor[] { inlineDescriptor };
        } else {
            return new ConfigDescriptor[0];
        }
    }

    /**
     * ��entry��������entry�е�descriptors�����б��С�
     * 
     * @param entry config entry
     * @param descriptors descriptors�б�
     */
    private void addConfigEntryRecursive(ConfigEntry entry, List descriptors) {
        if (entry == null) {
            return;
        }

        ConfigDescriptor[] entryDescriptors = entry.getGenerator().getConfigDescriptors();

        for (int i = 0; i < entryDescriptors.length; i++) {
            descriptors.add(entryDescriptors[i]);
        }

        ConfigEntry[] subEntries = entry.getSubEntries();

        for (int i = 0; i < subEntries.length; i++) {
            addConfigEntryRecursive(subEntries[i], descriptors);
        }
    }
}
