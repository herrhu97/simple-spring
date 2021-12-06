package cn.herrhu.springframework.aop.aspectj;

import cn.herrhu.springframework.aop.ClassFilter;
import cn.herrhu.springframework.aop.MethodMatcher;
import cn.herrhu.springframework.aop.PointCut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/6 16:41
 */
public class AspectJExpressionPointCut implements ClassFilter, MethodMatcher, PointCut {

    private static final Set<PointcutPrimitive> SUPPORT_PRIMITIVES = new HashSet<PointcutPrimitive>();

    static {
        SUPPORT_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointCut(String pointcutExpression) {
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
