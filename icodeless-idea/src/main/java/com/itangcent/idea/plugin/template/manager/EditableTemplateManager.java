package com.itangcent.idea.plugin.template.manager;


import com.itangcent.icode.api.TemplateManager;

public interface EditableTemplateManager extends TemplateManager {

    boolean isModified();

    void save();
}
