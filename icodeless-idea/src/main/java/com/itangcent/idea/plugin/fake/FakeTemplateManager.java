package com.itangcent.idea.plugin.fake;

import com.itangcent.icode.api.Template;
import com.itangcent.icode.api.TemplateContext;
import com.itangcent.idea.plugin.template.manager.EditableTemplateManager;

public class FakeTemplateManager implements EditableTemplateManager {
    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void save() {

    }

    @Override
    public Template findByName(String name) {
        return null;
    }

    @Override
    public Template findByName(String name, TemplateContext context) {
        return null;
    }
}
