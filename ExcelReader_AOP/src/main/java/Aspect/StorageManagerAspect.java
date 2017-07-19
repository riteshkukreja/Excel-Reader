/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.util.StopWatch;

/**
 *
 * @author admin
 */
@Aspect
public class StorageManagerAspect {
    
    StopWatch stopWatch = new StopWatch();
    
    //@Before("execution(* Model.FileManager.*(..))")
    public void beforeStoring(JoinPoint joinPoint) {
        stopWatch.start(joinPoint.getSignature().toShortString());
    } 
    
    //@After("execution(* Model.FileManager.*(..))")
    public void afterStoring(JoinPoint joinPoint) {
        stopWatch.stop();
        
        System.out.print("Process " + joinPoint.getSignature().toShortString() + " with [");
        Object[] params = joinPoint.getArgs();
        for(Object param: params) {
            System.out.print(param + ",");
        }
        System.out.print("] finished in "+ stopWatch.prettyPrint() +"\n");
    }
    
    @Around("execution(* Model.FileManager.*(..))")
    public Object aroundStoring(ProceedingJoinPoint joinPoint) throws Throwable {
        stopWatch.start(joinPoint.getSignature().toShortString());
        Object ll = joinPoint.proceed();
        stopWatch.stop();
        System.out.print("Around Process " + joinPoint.getSignature().toShortString() + " with [");
        Object[] params = joinPoint.getArgs();
        for(Object param: params) {
            System.out.print(param + ",");
        }
        System.out.print("] finished in "+ stopWatch.prettyPrint() +"\n");
        return ll;
    }
    
}
