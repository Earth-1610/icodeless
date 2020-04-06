package com.itangcent.icode.idea.plugin.fake;

import com.itangcent.icode.api.EditableTemplate;
import com.itangcent.icode.api.EditableTemplateManager;
import com.itangcent.icode.api.TemplateContext;

import java.util.List;

public class FakeTemplateManager implements EditableTemplateManager {

    @Override
    public EditableTemplate findByName(String name) {
        return null;
    }

    @Override
    public EditableTemplate findByName(String name, TemplateContext context) {
        return null;
    }

    @Override
    public boolean isAnyModified() {
        return false;
    }

    @Override
    public List<EditableTemplate> allModified() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void save() {

    }
}
