package com.itangcent.icode.api;

import java.util.List;

public abstract class AbstractTemplateEditor implements TemplateEditor {

    private EditableTemplate template;

    public AbstractTemplateEditor(EditableTemplate template) {
        this.template = template;
    }

    protected abstract String name();

    protected abstract String displayName();

    protected abstract List<TemplateParam> params();

    protected abstract boolean alwaysCheckBeforeRender();

    protected abstract String type();

    protected abstract String content();

    @Override
    public void save() {
        template.setName(name());
        template.setDisplayName(displayName());
        template.setParams(params());
        template.setType(type());
        template.setContent(content());
        template.setAlwaysCheckBeforeRender(alwaysCheckBeforeRender());
        saveTemplate(template);
    }

    protected abstract void saveTemplate(EditableTemplate template);
}
