package com.itangcent.icode.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic UIContainer is a component that can contain other AWT components.
 *
 * @see java.awt.Container
 */
public interface UIContainer {

    /**
     * Appends the specified component to the end of this container.
     *
     * @param comp the component to be added
     * @return the component argument
     */
    Component add(Component comp);

    /**
     * Removes the specified component from this container.
     */
    void remove(Component child);

    /**
     * Remove all sub components which has be added by {@link #add}
     */
    void clear();

    static UIContainer of(Container container) {
        if (container instanceof JScrollPane) {
            return new JScrollPaneUIContainer((JScrollPane) container);
        } else {
            return new BaseUIContainer(container);
        }
    }

    abstract class AbstractUIContainer implements UIContainer {

        /**
         * cached components which has be added by {@link #add}.
         */
        private List<Component> subs = new ArrayList<>();

        /**
         * Appends the specified component to the end of this container.
         * And cached it to {@link #subs}.
         *
         * @param comp the component to be added
         * @return
         */
        @Override
        public Component add(Component comp) {
            subs.add(comp);
            return doAdd(comp);
        }

        /**
         * Removes the specified component from this container.
         * And remove it from {@link #subs}
         */
        @Override
        public void remove(Component child) {
            subs.remove(child);
            doRemove(child);
        }

        /**
         * Appends the specified component to the end of this container.
         */
        abstract Component doAdd(Component comp);

        /**
         * Removes the specified component from this container.
         */
        abstract void doRemove(Component child);

        /**
         * Remove all sub components which has be added by {@link #add}
         * And clear cached {@link #subs}
         */
        @Override
        public void clear() {
            if (!subs.isEmpty()) {
                for (Component sub : subs) {
                    doRemove(sub);
                }
            }
            subs.clear();
        }
    }

    class BaseUIContainer extends AbstractUIContainer {

        private Container container;

        private BaseUIContainer(Container container) {
            this.container = container;
        }

        @Override
        public Component doAdd(Component comp) {
            return container.add(comp);
        }

        @Override
        public void doRemove(Component child) {
            container.remove(child);
        }

    }

    class JScrollPaneUIContainer extends AbstractUIContainer {


        private JScrollPane container;

        private JScrollPaneUIContainer(JScrollPane container) {
            this.container = container;
        }

        @Override
        public Component doAdd(Component comp) {
            return container.getViewport().add(comp);
        }

        /**
         * Removes the <code>Viewport</code>s one lightweight child.
         */
        @Override
        public void doRemove(Component child) {
            container.getViewport().remove(child);
        }
    }

}
