package dk.apaq.billy.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ContactType {
    @JsonProperty("company")
    Company,
    @JsonProperty("person")
    Person
}
