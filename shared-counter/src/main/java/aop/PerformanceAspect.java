package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Queue;


/**
 * Simple aspect that measures method execution time using System.nanoTime()
 * Works with @aop.MeasurePerformance annotation
 */
@Aspect
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    @Around("execution(@aop.MeasurePerformance * *(..))")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        MeasurePerformance annotation = method.getAnnotation(MeasurePerformance.class);
        String description = annotation.value();

        Object[] args = joinPoint.getArgs();
        description = description + args[1].toString();

        Queue<Double> list = null;

        for (Object arg : args) {
            if (arg instanceof Queue<?>) {
                list = (Queue<Double>) arg;
                break;
            }
        }

        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();

        double durationMs = (endTime - startTime) / 1_000_000.0;
        list.add(durationMs);
        return result;
    }
}