package com.lattory.lattoryLotoBackEnd.core.events.listeners;

import com.lattory.lattoryLotoBackEnd.core.events.HistoryUserLoginEvent;
import com.lattory.lattoryLotoBackEnd.core.events.WingNotifyEvent;
import org.springframework.context.ApplicationListener;

public class HistoryUserLoginEventListener implements ApplicationListener<HistoryUserLoginEvent> {

    @Override
    public void onApplicationEvent(HistoryUserLoginEvent event) {
        
    }
}
