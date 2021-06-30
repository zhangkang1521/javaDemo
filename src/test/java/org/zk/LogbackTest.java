package org.zk;

import ch.qos.logback.classic.LoggerContext;
import org.junit.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.impl.StaticLoggerBinder;

public class LogbackTest {


	@Test
	public void test1() {
		ILoggerFactory factory = StaticLoggerBinder.getSingleton().getLoggerFactory();
		// LoggerContext 实现了ILoggerFactory接口
		LoggerContext loggerContext = (LoggerContext)factory;
		loggerContext.addTurboFilter(new DemoTurboFilter());
		Logger logger = factory.getLogger(LogbackTest.class.getName());
		logger.info("123");
		logger.warn("ok");
	}
}
