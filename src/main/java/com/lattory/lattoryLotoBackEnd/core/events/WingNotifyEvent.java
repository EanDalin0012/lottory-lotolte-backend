package com.lattory.lattoryLotoBackEnd.core.events;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import org.springframework.context.ApplicationEvent;

public class WingNotifyEvent extends ApplicationEvent {
    public WingNotifyEvent(Object source) {
        super(source);
    }
}
