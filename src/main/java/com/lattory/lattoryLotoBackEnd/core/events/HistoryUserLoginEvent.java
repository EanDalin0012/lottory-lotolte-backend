package com.lattory.lattoryLotoBackEnd.core.events;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import org.springframework.context.ApplicationEvent;

public class HistoryUserLoginEvent extends ApplicationEvent {
    public HistoryUserLoginEvent(Object source, JsonObject object) {
        super(source);
    }
}
