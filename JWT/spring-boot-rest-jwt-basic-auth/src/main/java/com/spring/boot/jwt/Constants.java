package com.spring.boot.jwt;

public interface Constants {

    public static final String JWT_SECRET_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_ISSUER = "MyApplication";
    public static final String JWT_ISSUER_SUBJECT = "JWT_TOKEN";

    public static final String JWT_CLAIMS_USERNAME = "username";
    public static final String JWT_CLAIMS_AUTHORITIES = "authorities";
}
