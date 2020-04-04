package com.itangcent.icode.api;

public class BaseTemplateContext extends AbstractTemplateContext {
    private Template template;
    private TemplateContext parent;

    private BaseTemplateContext(Template template, TemplateContext parent) {
        this.template = template;
        this.parent = parent;
    }

    @Override
    public Template template() {
        return template;
    }

    @Override
    public TemplateContext parent() {
        return parent;
    }

    @Override
    public BaseTemplateContext subContext(Template template) {
        return new BaseTemplateContext(template, this);
    }

    public static BaseTemplateContext of(Template template) {
        return new BaseTemplateContext(template, null);
    }
}
