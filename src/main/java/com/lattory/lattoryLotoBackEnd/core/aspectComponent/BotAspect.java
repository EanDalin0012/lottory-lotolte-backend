package com.lattory.lattoryLotoBackEnd.core.aspectComponent;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.events.WingNotifyEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import javax.inject.Inject;

@Aspect
@Component
public class BotAspect {
    @Inject
    private ApplicationEventPublisher eventPublisher;

    @Pointcut("execution(* com.lattory.lattoryLotoBackEnd.core.rest.UserRest.loadUserByUserName(..)))" )
    private void execute(){
        System.out.println("BotAspect Component execute");
    }

    @Before("execute()")
    public void beforeExecute() throws InterruptedException {
        System.out.println("BotAspect Component beforeExecute");
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("data", "testing");
        eventPublisher.publishEvent(new WingNotifyEvent(jsonObject));
    }

    @AfterReturning(pointcut = "execute()", argNames = "request, response", returning = "response")
    public void afterExecute(JoinPoint request, Object response) {
        System.out.println("BotAspect Component afterExecute");
//        eventPublisher.publishEvent(new WingBotEvent(getRequest(request, response)));
    }
}
