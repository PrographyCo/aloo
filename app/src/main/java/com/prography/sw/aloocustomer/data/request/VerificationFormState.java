package com.prography.sw.aloocustomer.data.request;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
public class VerificationFormState {

    @Nullable
    private Integer verificationErrorCode;

    private boolean isDataValid;

    public VerificationFormState(@Nullable Integer verificationErrorCode) {
        this.verificationErrorCode = verificationErrorCode;
        this.isDataValid = false;
    }

    public VerificationFormState(boolean isDataValid) {
        this.verificationErrorCode = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getVerificationErrorCode() {
        return verificationErrorCode;
    }


    public boolean isDataValid() {
        return isDataValid;
    }

    public void setVerificationErrorCode(@Nullable Integer verificationErrorCode) {
        this.verificationErrorCode = verificationErrorCode;
    }


}
