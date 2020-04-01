package com.itangcent.icode.api;

import java.util.List;

public abstract class AbstractTemplateEditor implements TemplateEditor {

    private EditableTemplate template;

    public AbstractTemplateEditor(EditableTemplate template) {
        this.template = template;
    }

    @Override
    public void show() {
        renderTemplate(template);
    }

    protected abstract void renderTemplate(Template template);

    protected abstract String name();

    protected abstract String displayName();

    protected abstract List<TemplateParam> params();

    protected abstract boolean alwaysCheckBeforeRender();

    protected abstract String type();

    protected abstract String content();

    @Override
    public void close() {
        template.setName(name());
        template.setDisplayName(displayName());
        template.setParams(params());
        template.setType(type());
        template.setContent(content());
        save(template);
    }

    protected abstract void save(Template template);
}
