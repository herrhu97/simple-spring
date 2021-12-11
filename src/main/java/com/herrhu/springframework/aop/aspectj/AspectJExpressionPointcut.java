package com.herrhu.springframework.aop.aspectj;

import com.herrhu.springframework.aop.ClassFilter;
import com.herrhu.springframework.aop.MethodMatcher;
import com.herrhu.springframework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 指明切面位置，包装ClassFilter、MethodMatcher（均由切面表达式提供），作为proxy需要的MethodMatcher使用
 * @author: HerrHu
 * @time: 2021/12/6 16:41
 */
public class AspectJExpressionPointcut implements ClassFilter, MethodMatcher, Pointcut {

    private static final Set<PointcutPrimitive> SUPPORT_PRIMITIVES = new HashSet<PointcutPrimitive>();

    static {
        SUPPORT_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String pointcutExpression) {
        PointcutParser pointcutParser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        SUPPORT_PRIMITIVES, this.getClass().getClassLoader()
                );
        this.pointcutExpression = pointcutParser.parsePointcutExpression(pointcutExpression);
    }

    @Override
    public boolean matches(Class<?> targetClass) {
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
