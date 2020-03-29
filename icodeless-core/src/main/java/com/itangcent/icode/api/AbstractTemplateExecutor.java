package com.itangcent.icode.api;

import java.util.Map;

public abstract class AbstractTemplateExecutor implements TemplateExecutor {

    protected abstract TemplateManager templateManager();

    protected abstract CodeRenderProviderManager codeRenderProviderManager();

    protected abstract TemplateParamInput templateParamInput();

    @Override
    public TemplateExecuteResult call(String name, Map<String, Object> params, TemplateContext context) {
        //find template
        Template template = templateManager().findByName(name, context);
        if (template == null) {
            return BaseTemplateExecuteResult.faild();
        }
        //call template with subContext
        return call(template, params, context.subContext(template));
    }

    public TemplateExecuteResult call(Template template, Map<String, Object> params, TemplateContext context) {
        try {
            CodeRenderProvider codeRenderProvider = codeRenderProviderManager().findByType(template.type());
            CodeRender codeRender = codeRenderProvider.buildRender(template.content());
            Map<String, Object> readParams = templateParamInput().readParams(template.params(), params);
            beforeRender(codeRender, readParams, context);
            TemplateExecuteResult result;
            try {
                result = codeRender.render(readParams, context);
                afterRender(codeRender, params, context, result, null);
            } catch (Throwable e) {
                result = BaseTemplateExecuteResult.error(template, e);
                afterRender(codeRender, params, context, result, e);
            }
            return result;
        } catch (Throwable e) {
            return BaseTemplateExecuteResult.error(template, e);
        }
    }

    protected void beforeRender(CodeRender codeRender, Map<String, Object> readParams, TemplateContext context) {


    }

    protected void afterRender(CodeRender codeRender, Map<String, Object> readParams, TemplateContext context,
                               TemplateExecuteResult result, Throwable error) {


    }
}
