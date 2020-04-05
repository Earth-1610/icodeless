package com.itangcent.idea.plugin.actions;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.SearchableConfigurable;
import com.itangcent.icode.api.EditableTemplateManager;
import com.itangcent.idea.plugin.ui.ICodeLessConfigurableGUI;

import javax.swing.*;

public class ICodeLessConfigurable implements SearchableConfigurable {

    private ICodeLessConfigurableGUI iCodeLessConfigurableGUI;

    private EditableTemplateManager templateManager = ServiceManager.getService(EditableTemplateManager.class);

    public boolean isModified() {
        return templateManager.isAnyModified();
    }

    public String getId() {
        return "preference.ICodeLessConfigurable";
    }

    public String getDisplayName() {
        return "ICodeLess";
    }

    public void apply() {
        templateManager.save();
    }

    public JComponent createComponent() {
        iCodeLessConfigurableGUI = new ICodeLessConfigurableGUI();

        iCodeLessConfigurableGUI.onCreate();
        return iCodeLessConfigurableGUI.getRootPanel();
    }

    public void reset() {
        iCodeLessConfigurableGUI.reset();
    }
}
