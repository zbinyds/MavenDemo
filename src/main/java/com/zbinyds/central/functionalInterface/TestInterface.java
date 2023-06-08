package com.zbinyds.central.functionalInterface;

import java.util.function.Predicate;

/**
 * @Package: com.zbinyds.central.annotate
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/5/23 22:51
 */

@FunctionalInterface
public interface TestInterface<T> extends Predicate<T> {

}
