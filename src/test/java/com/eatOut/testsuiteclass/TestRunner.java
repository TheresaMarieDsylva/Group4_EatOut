package com.eatOut.testsuiteclass;

import org.junit.runner.JUnitCore;

public class TestRunner {
    public static void main(String[] args) {
            JUnitCore.runClasses(TestSuite.class);
    }
}
