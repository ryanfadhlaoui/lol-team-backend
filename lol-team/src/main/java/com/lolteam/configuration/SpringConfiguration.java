package com.lolteam.configuration;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableAsync
@EnableScheduling
//http://www.javarticles.com/2016/01/spring-componentscan-annotation-example.html
//@ComponentScan(basePackages = { "com.lolteam.configuration", "com.lolteam.controllers", "com.lolteam.services", "com.lolteam.dao", "com.lolteam.myteam.components" })
//@ComponentScan(basePackages = { "com.lolteam" })
public class SpringConfiguration {

	@Bean
	public Executor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setMaxPoolSize(25);
	    executor.setQueueCapacity(100);
	    executor.initialize();
	    return executor;
	}

	@Bean
	public Executor taskScheduler() {
	    // set properties if required 
	    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
	    scheduler.setPoolSize(10);
	    return scheduler;
	}   
}