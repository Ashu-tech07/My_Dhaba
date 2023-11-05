package com.mydhaba;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("execution(* com.mydhaba.*.*.*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		logger.debug("Entering " + methodName + "() method inside [ " + joinPoint.getSignature().getDeclaringTypeName()
				+ " ] with following arguments");
		for (Object arg : args)
			logger.debug("arg : " + arg);
	}

	@AfterReturning(pointcut = "execution(* com.mydhaba.*.*.*(..))", returning = "returnValue")
	public void afterAdvice(JoinPoint joinPoint, Object returnValue) {
		String methodName = joinPoint.getSignature().getName();
		logger.debug("Exited " + methodName + "() method inside [" + joinPoint.getSignature().getDeclaringTypeName()
				+ " ] with return value : " + returnValue);
	}
}