package org.example;

import shared.Client;

import java.sql.Connection;

public class ClientDAOImpl2 extends AbstractDAO<Client> {
    public ClientDAOImpl2(Connection conn, String table) {
        super(conn, table);
    }
}
