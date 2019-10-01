package kr.co.itcen.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
	
	//PointCut 기술 방법 1
	// 반환타임 패키지.클래스이름.메소드명(파라미터타입) throws 예외
	// 메소드를 정확하게 기술할때
	@Before("execution(ProductVo kr.co.itcen.aoptest.ProductService.find(String))")
	public void beforeAdvice() {
		System.out.println("---- Before advice ----");
	}
	
	//1. PointCut 기술 방법1에서 throws 구문은 생략이 가능하다.
	//2. 리턴타입은 *로 와일드 카드 사용가능
	//3. 파라미터 기술을 .. 으로 축약(파라미터의 갯수, 형식, 순서 상관없이)
	// 하나의 클래스의 오버로드가 안된 메소드를 지정할 때
	@After("execution(* kr.co.itcen.aoptest.ProductService.find(..))")
	public void afterAdvice() {
		System.out.println("---- After advice ----");
	}
	
	// 메소드 이름은 *로 모든 메소드 지정
	// 하나의 클래스의 모든 메소드를 기술
	@AfterReturning("execution(* kr.co.itcen.aoptest.ProductService.*(..))")
	public void afterReturningAdvice() {
		System.out.println("---- AfterReturning advice ----");
	}
	
	//1. 패키지 이름을 *..*로 모든 패키지 지정
	// 특정 클래스의 모든 메소드를 지정할 때 보통 많이 쓰는 방법
	@AfterThrowing(value="execution(* *..*.ProductService.*(..))", throwing="ex")
	public void afterThrowingAdvice(Throwable ex) {
		System.out.println("---- AfterThrowing advice ---- : " + ex);
	}
	
	
	@Around("execution(* *..*.ProductService.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		//before advice
		System.out.println("---- @Around(before) ----");
		
		//1. PointCut이 되는 메소드 호출(파라미터는 그대로 전달)
		Object result = pjp.proceed();
		
		//2. PointCut이 되는 메소드 호출(파라미터 변경)
		//Object[] parameters = {"Camera"};
		//Object result = pjp.proceed(parameters);
		
		//after advice
		System.out.println("---- @Around(after) ----");
		
		return result;
	}

}
