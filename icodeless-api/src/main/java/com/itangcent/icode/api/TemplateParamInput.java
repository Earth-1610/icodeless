package com.itangcent.icode.api;

import java.util.List;
import java.util.Map;

public interface TemplateParamInput {

    Map<String, Object> readParams(List<TemplateParam> params, Map<String, Object> defaultParams);
}
