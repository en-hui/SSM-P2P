package fun.enhui.listener;

import fun.enhui.service.LogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 初始化超级管理员的监听器
 *
 * 再spring中，实现了ApplicationListener接口的类就可以作为spring的监听器来监听spring中特殊的事件
 * 再spring中，ApplicationEvent这个类相当于所有的事件，如果我们的监听器
 *       继承ApplicationListener<ApplicationEvent>,相当于我们这个监听器监听的是spring里面所有的消息
 *
 *       ContextRefreshedEvent是spring启动完成的事件
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LogininfoService logininfoService;
    /**
     * 监听到指定事件后执行的方法
     * ContextRefreshedEvent就是这次监听到的事件对象
     */

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logininfoService.initAdmin();
    }
}
