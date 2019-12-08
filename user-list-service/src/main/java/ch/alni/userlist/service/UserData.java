package ch.alni.userlist.service;

import com.google.auto.value.AutoValue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import org.jetbrains.annotations.Nullable;

@AutoValue
@JsonDeserialize(builder = UserData.Builder.class)
public abstract class UserData {

    public static Builder builder() {
        return new AutoValue_UserData.Builder();
    }

    @JsonGetter
    @Nullable
    public abstract String getFirstName();

    @JsonGetter
    public abstract String getLastName();

    @JsonGetter
    public abstract String getEmail();

    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "set")
    public abstract static class Builder {

        @JsonCreator
        static Builder create() {
            return builder();
        }

        @JsonSetter
        public abstract Builder setLastName(String value);

        @JsonSetter
        public abstract Builder setFirstName(String value);

        @JsonSetter
        public abstract Builder setEmail(String value);

        public abstract UserData build();
    }
}
