package cn.herrhu.springframework.aop.aspectj;

import cn.herrhu.springframework.aop.Pointcut;
import cn.herrhu.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/7 15:54
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    //切面
    private AspectJExpressionPointcut pointcut;

    //具体的拦截方法
    private Advice advice;

    //表达式
    private String expression;

    public void setPointcut(AspectJExpressionPointcut pointcut) {
        this.pointcut = pointcut;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            return new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
