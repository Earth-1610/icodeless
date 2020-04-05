package com.itangcent.icode.api;


public interface EditableTemplateManager extends TemplateManager {

    /**
     * Find template by name.
     * the Template get from EditableTemplateManager should be editable
     *
     * @param name -template name
     * @return the template which is editable
     */
    @Override
    EditableTemplate findByName(String name);

    /**
     * Find template by name and context.
     * The Template get from EditableTemplateManager should be editable
     *
     * @param name -template name
     * @return the template which is editable
     */
    @Override
    EditableTemplate findByName(String name, TemplateContext context);

    /**
     * Return true if any template was modified.
     */
    boolean isAnyModified();

    /**
     * Save all changes.
     */
    void save();
}
