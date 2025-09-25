package edu.smcrm.util;

// Simple validators and placeholders for comparator lambdas in README mapping
public class Validators {
    public static boolean isEmail(String s){ return s!=null && s.contains(\"@\"); }
}