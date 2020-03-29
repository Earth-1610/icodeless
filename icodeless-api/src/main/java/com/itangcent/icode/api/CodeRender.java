package com.itangcent.icode.api;

import java.util.Map;

public interface CodeRender {

    TemplateExecuteResult render(Map<String, Object> params, TemplateContext context);
}
