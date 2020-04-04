package com.itangcent.icode.api;

public class EmptyTemplateContext extends AbstractTemplateContext {

    EmptyTemplateContext() {
    }

    @Override
    public Template template() {
        return null;
    }

    @Override
    public TemplateContext parent() {
        return null;
    }

    @Override
    public BaseTemplateContext subContext(Template template) {
        return BaseTemplateContext.of(template);
    }
}
