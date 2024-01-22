package com.mazzocchi.video_app.util;

import com.mazzocchi.video_app.user.dto.*;
import jakarta.validation.*;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserRegistrationRequest user = (UserRegistrationRequest) obj;
        return user.getPassword().equals(user.getPasswordConfirmation());
    }
}