package com.itangcent.icode.api;

import java.util.List;
import java.util.Map;

public interface TemplateParamEditor {

    Map<String, Object> createParamEditors(List<TemplateParam> params, Map<String, Object> defaultParams);
}
