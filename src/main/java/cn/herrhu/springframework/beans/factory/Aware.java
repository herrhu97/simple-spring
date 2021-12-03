package cn.herrhu.springframework.beans.factory;

/**
 * 标记接口，实现该接口可以被Spring容器感知
 *
 * Maker superinterface indicating that a bean is eligible to be notified by the
 * spring container of a particular framework object through a callback-style method.
 * Actual method signature is determined by individual sub interfaces, but should
 * typically consist of just one void-returning method that accepts a single argument
 */
public interface Aware {
}
