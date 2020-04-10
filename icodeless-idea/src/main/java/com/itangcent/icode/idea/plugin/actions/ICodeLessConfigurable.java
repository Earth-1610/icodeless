package com.itangcent.icode.idea.plugin.actions;

import com.intellij.openapi.options.SearchableConfigurable;
import com.itangcent.icode.api.EditableTemplateManager;
import com.itangcent.icode.idea.plugin.ui.ICodeLessConfigurableGUI;
import com.itangcent.icode.spi.ServiceLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ICodeLessConfigurable implements SearchableConfigurable {

    private ICodeLessConfigurableGUI iCodeLessConfigurableGUI;

    private EditableTemplateManager templateManager = ServiceLoader.getService(EditableTemplateManager.class);

    public boolean isModified() {
        return templateManager.isAnyModified();
    }

    @NotNull
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
        iCodeLessConfigurableGUI.onCreate(templateManager);
        return iCodeLessConfigurableGUI.getRootPanel();
    }

    public void reset() {
        iCodeLessConfigurableGUI.reset(templateManager);
    }
}
