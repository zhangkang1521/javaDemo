package org.zk;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class SampleFilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getMessage().contains("ok")) {
			return FilterReply.ACCEPT; // 立即接受，跳过其他过滤器
		} else {
			return FilterReply.DENY; // 直接丢弃
			// return FilterReply.NEUTRAL; // 交个剩下的过滤器判断
		}
	}
}
