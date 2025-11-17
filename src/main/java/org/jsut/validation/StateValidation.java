package org.jsut.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jsut.anno.State;

public class StateValidation implements ConstraintValidator<State,String> {
    /**
     * 校验逻辑
     * @param s 被校验的参数
     * @param constraintValidatorContext 上下文
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if (s == null){
            return false;
        }

        return s.equals("已发布") || s.equals("草稿");
    }
}
