package com.accountsservices.Security;

public class JWTUtils {
    public static final String SECRET="is secret :^D";
    public static final String AUTH_HEADER="Authorization";
    public static final long EXPIRE_ACCESS=15*60*1000; // 15min
    public static final long EXPIRE_REFRESH=600*60*1000; //10h
    public static final String PREFIX="Bearer ";


}
