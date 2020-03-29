package com.itangcent.icode.api;

public class BaseTemplateExecuteResult implements TemplateExecuteResult {
    private Template template;
    private boolean success;
    private Throwable error;

    protected BaseTemplateExecuteResult(Template template, boolean success, Throwable error) {
        this.template = template;
        this.success = success;
        this.error = error;
    }

    @Override
    public Template template() {
        return template;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public Throwable error() {
        return error;
    }

    public static BaseTemplateExecuteResult success(Template template) {
        return new BaseTemplateExecuteResult(template, true, null);
    }

    public static BaseTemplateExecuteResult faild() {
        return new BaseTemplateExecuteResult(null, false, null);
    }

    public static BaseTemplateExecuteResult faild(Template template) {
        return new BaseTemplateExecuteResult(template, false, null);
    }

    public static BaseTemplateExecuteResult error(Throwable throwable) {
        return new BaseTemplateExecuteResult(null, false, throwable);
    }

    public static BaseTemplateExecuteResult error(Template template, Throwable throwable) {
        return new BaseTemplateExecuteResult(template, false, throwable);
    }
}
