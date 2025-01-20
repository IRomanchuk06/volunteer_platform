package com.example.volunteer_platform.client.utils;

import lombok.Getter;

import static com.example.volunteer_platform.client.constants.MenuConstants.INVALID_ACCOUNT_TYPE;

@Getter
public enum AccountType {
    VOLUNTEER(1),
    CUSTOMER(2);

    private final int value;

    AccountType(int value) {
        this.value = value;
    }

    public static AccountType fromValue(int value) {
        for (AccountType type : AccountType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException(INVALID_ACCOUNT_TYPE + value);
    }
}
