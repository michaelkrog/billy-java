package dk.apaq.billy.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SubscriptionPeriod {
    @JsonProperty("monthly")
    Monthly, 
    @JsonProperty("yearly")
    Yearly
}
