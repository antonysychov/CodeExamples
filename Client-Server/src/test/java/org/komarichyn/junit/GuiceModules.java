package org.komarichyn.junit;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GuiceModules {
    Class<?>[] value();
}
