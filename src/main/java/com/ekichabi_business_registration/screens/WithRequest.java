package com.ekichabi_business_registration.screens;

/**
 * If a screen marked with @class WithRequest successfully transit to the next screen,
 * the request will be performed.
 */
public interface WithRequest {
    void doRequest();
}
