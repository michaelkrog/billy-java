package com.previsto.billy.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentTermsMode {

    @JsonProperty("unknown")
    Unknown,
    @JsonProperty("date")
    Date
}