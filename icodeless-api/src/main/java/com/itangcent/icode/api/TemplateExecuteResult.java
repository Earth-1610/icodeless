package com.itangcent.icode.api;

public interface TemplateExecuteResult {

    Template template();

    boolean isSuccess();

    Throwable error();
}
