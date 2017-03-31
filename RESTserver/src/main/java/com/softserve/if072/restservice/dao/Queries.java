package com.softserve.if072.restservice.dao;

/**
 * This class collects SQL queries.
 *
 * @author Oleh Pochernin
 */
public final class Queries {
    public static final String SELECT_ALL_USERS = "SELECT id, name, email, password, role, is_enabled, premium_expires_time FROM user";
    public static final String SELECT_USER_BY_ID = "SELECT id, name, email, password, role, is_enabled, premium_expires_time FROM user WHERE id = #{id}";
    public static final String SELECT_USER_BY_USERNAME = "SELECT id, name, email, password, role, is_enabled, premium_expires_time FROM user WHERE email = #{username}";
    public static final String INSERT_USER = "INSERT INTO user (name, email, password, role, is_enabled) VALUES (#{name}, #{email}, #{password}, #{role}, #{isEnabled})";
    public static final String UPDATE_USER = "UPDATE user SET name = #{name}, email = #{email}, password = #{password}, role = #{role}, is_enabled = #{isEnabled}, premium_expires_time = #{premiumExpiresTime} WHERE id=#{id}";
    public static final String DELETE_USER = "UPDATE user SET is_enabled = 0 WHERE id = #{id}";

    private Queries() {
        //this prevents even the native class from calling this ctor as well:
        throw new AssertionError();
    }
}
