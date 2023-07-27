package ua.kiev.prog;

public interface EntityIntf {
    String getName();
    int getAge();
}

// App -> JPA -> H! -> JDBC -> DB
// App -> H -> JDBC -> DB -?