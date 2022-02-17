package com.ekichabi_business_registration.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UssdRequest {
    private String sessionId;
    private String phoneNumber;
    private String networkCode;
    private String serviceCode;
    private String text;
}
