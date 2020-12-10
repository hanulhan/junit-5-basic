package com.hanulhan.junit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // Only One object for all Tests. Not recommended

class MathUtilsTest {

	MathUtils mathUtils;
	TestInfo testInfo;
	TestReporter testReporter;
	
	@BeforeAll
	static void InitAll() {
		// needs to be static. Otherwise the object does not exists
		System.out.println("BeforeAll");
	}

	@org.junit.jupiter.api.AfterAll
	static void AfterAll() {
		// needs to be static. Otherwise the object is already destroyed
		System.out.println("AfterAll");
	}
	
	@BeforeEach
	void InitEach(TestInfo testInfo, TestReporter testReporter) {
		System.out.println("BeforeEach");
		this.testInfo= testInfo;
		this.testReporter= testReporter;
		mathUtils = new MathUtils();
	}
	
	@AfterEach
	void cleanupEach() {
		System.out.println("AferEach");
		
	}
	
	@Test
	@DisplayName("Testing add method 1")
	@EnabledOnOs(OS.LINUX)
	@Tag("Math")
	void testAdd1() {
		int expected = 2;
		int actual = mathUtils.add(1, 1);
		
		assertEquals(expected, actual);
	}

	@Nested
	@DisplayName("Testing add method 2")
	@Tag("Math")
	class AddTest {
		@Test
		@DisplayName("when adding two positive numbers")
		void testAddPositive() {
			assertEquals(2, mathUtils.add(1, 1), "should return the right sum");
		}
		
		@Test
		@DisplayName("when adding two negative numbers")
		void testAddNegative() {

			int expexcted = -2;
			int actual = mathUtils.add(-1,  -1);
			// This is an expensive string. Every time this is computed even before if the test pass
			//assertEquals(expexcted, actual, "should return sum " + expexcted + " but received " + actual);
		
			// Better as a lambda. Only when test fails
			assertEquals(expexcted, actual, () -> "should return sum " + expexcted + " but received " + actual);
		}
	}
	
	
	@Test
	@EnabledOnJre(JRE.JAVA_11)
	@Tag("Math")  // 
	void testDevide() {
		boolean isServerUp= true;
		
		assumeTrue(isServerUp);	// Test on external events necessary for the test.
		assertThrows(ArithmeticException.class, () -> mathUtils.divide(1,0), "divide by zero should throw");
	}
	
	@Test
	@DisplayName("Testing multiply")
	@Tag("Math")
	void testMultiply() {
		// TestInfo gets Information about the Test
		String value= "Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags();

		// TestReporter publish the information
		testReporter.publishEntry(value);
		
		// New in JUnit5. Bunch of asserts. Don't need to use @Nested
		// Using Lambdas

		
		assertAll(
				() -> assertEquals(4, mathUtils.multiply(2, 2)),
				() -> assertEquals(0, mathUtils.multiply(2, 0)),
				() -> assertEquals(-2, mathUtils.multiply(2, -1))
				);
	}
	
	@RepeatedTest(5)
	@Tag("Circle")
	void testComputeCircleRadius(RepetitionInfo repetitionInfo)	{
		// repetitionInfo.getCurrentRepetition();
		assertEquals(314.1592653589793, mathUtils.computeCircleArea(10), "Should return right circle area");
	}


	@Test
	@Disabled
	void testDisabled() {
		fail("This Test will fail");
	}


}
