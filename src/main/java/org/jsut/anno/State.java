package org.jsut.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.jsut.validation.StateValidation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Constraint(
        validatedBy = {StateValidation.class}//指定提供校验接口的类
)
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    // 提示信息(校验失败)
    String message() default "state参数的值只能是已发布或者草稿";
    // 分组
    Class<?>[] groups() default {};
    // 负载 获取到State 注解的属性值
    Class<? extends Payload>[] payload() default {};
}
