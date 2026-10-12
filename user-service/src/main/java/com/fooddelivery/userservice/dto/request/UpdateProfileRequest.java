package com.fooddelivery.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateProfileRequest {

    private String name;
    private String phone;

    @JsonCreator
    public UpdateProfileRequest(
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
}
