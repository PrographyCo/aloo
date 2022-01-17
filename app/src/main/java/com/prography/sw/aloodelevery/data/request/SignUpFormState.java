package com.prography.sw.aloodelevery.data.request;

import androidx.annotation.Nullable;

/**
 * Data validation state of the SignUp form.
 */
public class SignUpFormState {

    @Nullable
    private Integer nameError;

    @Nullable
    private Integer phoneError;

    @Nullable
    private Integer passwordError;

    @Nullable
    private Integer confirmPasswordError;

    @Nullable
    private Integer cityError;

    @Nullable
    private Integer genderError;


    private boolean isDataValid;

    public SignUpFormState(@Nullable Integer nameError, @Nullable Integer phoneError, @Nullable Integer passwordError, @Nullable Integer confirmPasswordError, @Nullable Integer cityError, @Nullable Integer genderError) {
        this.nameError = nameError;
        this.phoneError = phoneError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.cityError = cityError;
        this.genderError = genderError;
        this.isDataValid = false;
    }

    public SignUpFormState(boolean isDataValid) {
        this.nameError = null;
        this.phoneError = null;
        this.passwordError = null;
        this.confirmPasswordError = null;
        this.cityError = null;
        this.genderError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getFirstNameError() {
        return nameError;
    }

    @Nullable
    public Integer getPhoneError() {
        return phoneError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    @Nullable
    public Integer getCountryError() {
        return cityError;
    }

    @Nullable
    public Integer getGenderError() {
        return genderError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
