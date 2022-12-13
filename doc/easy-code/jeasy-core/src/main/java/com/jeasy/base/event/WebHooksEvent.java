package com.jeasy.base.event;

import org.springframework.context.ApplicationEvent;

/**
 * WebHooks事件
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class WebHooksEvent extends ApplicationEvent {

    private static final long serialVersionUID = 3443109525461436619L;

    public WebHooksEvent(final Object source) {
        super(source);
    }

}
