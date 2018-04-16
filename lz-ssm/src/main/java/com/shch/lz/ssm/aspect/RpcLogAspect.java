package com.shch.lz.ssm.aspect;

import com.alibaba.dubbo.rpc.RpcContext;
import com.shch.lz.ssm.util.key.SystemClock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Link at 21:25 on 3/29/18.
 */
public class RpcLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcLogAspect.class);
    private long startTime = 0L;
    private long endTime = 0L;

    @Before("execution(* *..rpc..*.*(..)")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        LOGGER.debug("doBeforeInServiceLayer");
//        startTime = System.currentTimeMillis();
        startTime = SystemClock.now();
    }

    @After("execution(* *..rpc..*.*(..)")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        LOGGER.debug("doAfterInServiceLayer");
    }

    @Around("execution(* *..rpc..*.*(..)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        boolean consumerSide = RpcContext.getContext().isConsumerSide();
        String ip = RpcContext.getContext().getRemoteHost();
        String rpcUrl = RpcContext.getContext().getUrl().getParameter("application");
        LOGGER.info("consumerSide={}, ip={}, url={}", consumerSide, ip, rpcUrl);
        return result;
    }
}
