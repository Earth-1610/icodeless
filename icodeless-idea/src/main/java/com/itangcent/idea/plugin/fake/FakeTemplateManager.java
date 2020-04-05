package com.itangcent.idea.plugin.fake;

import com.itangcent.icode.api.EditableTemplate;
import com.itangcent.icode.api.EditableTemplateManager;
import com.itangcent.icode.api.TemplateContext;

public class FakeTemplateManager implements EditableTemplateManager {
    @Override
    public boolean isAnyModified() {
        return false;
    }

    @Override
    public void save() {

    }

    @Override
    public EditableTemplate findByName(String name) {
        return null;
    }

    @Override
    public EditableTemplate findByName(String name, TemplateContext context) {
        return null;
    }
}
