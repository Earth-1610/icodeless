package com.itangcent.icode.api;

import java.util.Map;

/**
 * this render will output result to a file.
 */
public interface SingleOutputCodeRender extends CodeRender {

    @Override
    TemplateExecuteOutputFileResult render(Map<String, Object> params, TemplateContext context);

    void setOutPutPath(String path);
}
