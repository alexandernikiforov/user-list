package ch.alni.userlist.service;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Admin {
    public static Admin create(String name, String email) {
        return new AutoValue_Admin(name, email);
    }

    public abstract String getName();

    public abstract String getEmail();
}
