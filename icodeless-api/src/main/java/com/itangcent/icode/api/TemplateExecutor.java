package com.itangcent.icode.api;

import java.util.Map;

public interface TemplateExecutor {

    /**
     * call the template with special params
     */
    default TemplateExecuteResult call(String name, Map<String, Object> params) {
        return call(name, params, BaseTemplateContext.empty());
    }

    /**
     * call the template with special params and context
     */
    TemplateExecuteResult call(String name, Map<String, Object> params, TemplateContext context);
}
