package dk.apaq.billy.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum VatPeriod {
    @JsonProperty("quarterly")
    Quarterly,
    @JsonProperty("semianually")
    SemiAnnually;
}
