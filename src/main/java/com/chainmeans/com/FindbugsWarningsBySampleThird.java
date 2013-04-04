package com.chainmeans.com;

public class FindbugsWarningsBySampleThird {
	public static void main(final String[] args) {
		 
        System.out.println("\nFindbugs Sample for LEST_LOST_EXCEPTION_STACK_TRACE ");
        // Confidence: Normal, Rank: Scariest (2)
        // Pattern: LEST_LOST_EXCEPTION_STACK_TRACE 
        // Type: LEST, Category: CORRECTNESS (Correctness)
        //
        // WRONG
        try {
            FindbugsWarningsBySampleThird.lestLostExceptionStackTraceWRONG(null);
        } catch (final IllegalArgumentException e) {
            System.out.println("   - Exception Cause: " + e.getCause());
        }
        // CORRECT
        try {
            FindbugsWarningsBySampleThird.lestLostExceptionStackTraceCORRECT(null);
        } catch (final IllegalArgumentException e) {
            System.out.println("   - Exception Cause: " + e.getCause());
        }
 
        System.out.println("\nFindbugs Sample for RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE");
        // Confidence: High, Rank: Scary (9)
        // Pattern: RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE 
        // Type: RCN, Category: CORRECTNESS (Correctness)
        //
        // WRONG
        try {
            FindbugsWarningsBySampleThird.rcnRedundantNullcheckWouldHaveBeenANpeWRONG(null);
        } catch (final NullPointerException e) {
            System.out.println("   - Exception Cause: " + e.getCause());
        }
        // CORRECT
        FindbugsWarningsBySampleThird.rcnRedundantNullcheckWouldHaveBeenANpeCORRECT(null);
 
        final int randomInput = 1 + (int) Math.round(Math.random() * 1.0);
        System.out.println("\nFindbugs Sample for SF_SWITCH_FALLTHROUGH");
        // Confidence: Normal, Rank: Of Concern (17)
        // Pattern: SF_SWITCH_FALLTHROUGH 
        // Type: SF, Category: STYLE (Dodgy code)
        //
        // WRONG
        FindbugsWarningsBySampleThird.sfSwitchFallthroughWRONG(randomInput);
        // CORRECT
        FindbugsWarningsBySampleThird.sfSwitchFallthroughCORRECT(randomInput);
 
        System.out.println("\nFindbugs Sample for SF_DEAD_STORE_DUE_TO_SWITCH_FALLTHROUGH_TO_THROW");
        // Confidence: High, Rank: Scariest (1)
        // Pattern: SF_DEAD_STORE_DUE_TO_SWITCH_FALLTHROUGH_TO_THROW 
        // Type: SF, Category: CORRECTNESS (Correctness)
        // 
        // WRONG
        try {
            FindbugsWarningsBySampleThird
                    .sfDeadStroreDueToSwitchFallthroughToThrowWRONG(randomInput);
        } catch (final IllegalArgumentException e) {
            System.out.println("   - ERROR:" + e.getMessage());
        }
        // CORRECT
        FindbugsWarningsBySampleThird.sfDeadStroreDueToSwitchFallthroughToThrowCORRECT(randomInput);
 
        System.out.println("\nFindbugs Sample for SF_DEAD_STORE_DUE_TO_SWITCH_FALLTHROUGH");
        // Confidence: High, Rank: Scariest (1)
        // Pattern: SF_DEAD_STORE_DUE_TO_SWITCH_FALLTHROUGH 
        // Type: SF, Category: CORRECTNESS (Correctness)
        //
        // WRONG
        FindbugsWarningsBySampleThird.sfDeadStroreDueToSwitchFallthroughWRONG(randomInput);
        // CORRECT
        FindbugsWarningsBySampleThird.sfDeadStroreDueToSwitchFallthroughCORRECT(randomInput);
 
    }
 
    private static void lestLostExceptionStackTraceWRONG(final String text) {
        try {
            System.out.println(text.length());
        } catch (final NullPointerException e) {
            // Next line should show LEST_LOST_EXCEPTION_STACK_TRACE
            throw new IllegalArgumentException("Stupid error:" + e);
        }
    }
 
    private static void lestLostExceptionStackTraceCORRECT(final String text) {
        try {
            System.out.println(text.length());
        } catch (final NullPointerException e) {
            throw new IllegalArgumentException("Stupid error:", e);
        }
    }
 
    private static void rcnRedundantNullcheckWouldHaveBeenANpeWRONG(final String text) {
        // Next line should show RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE
        System.out.println(text.length());
        if (null == text)
            return;
    }
 
    private static void rcnRedundantNullcheckWouldHaveBeenANpeCORRECT(final String text) {
        if (null == text)
            return;
        System.out.println(text.length());
    }
 
    private static void sfSwitchFallthroughWRONG(final int input) {
        switch (input) {
        case 1:
            // Next line should show SF_SWITCH_FALLTHROUGH
            System.out.println("   - enter case 1");
        case 2:
            System.out.println("   - enter case 2");
        }
    }
 
    private static void sfSwitchFallthroughCORRECT(final int input) {
        switch (input) {
        case 1:
            System.out.println("   - enter case 1");
            break;
        case 2:
            System.out.println("   - enter case 2");
        }
    }
 
    private static int sfDeadStroreDueToSwitchFallthroughToThrowWRONG(final int input) {
 
        int result;
        switch (input) {
        case 0:
            System.out.println("   - enter case 0");
            result = 4;
            break;
        case 1:
            System.out.println("   - enter case 1");
            result = 2;
            break;
        case 2:
            System.out.println("   - enter case 2");
            result = 1;
        default:
            // Next line should show SF_DEAD_STORE_DUE_TO_SWITCH_FALLTHROUGH_TO_THROW 
            throw new IllegalArgumentException();
        }
        return result;
    }
 
    private static int sfDeadStroreDueToSwitchFallthroughToThrowCORRECT(final int input) {
 
        int result = 0;
        switch (input) {
        case 1:
            System.out.println("   - enter case 0");
            result = 2;
            break;
        case 2:
            System.out.println("   - enter case 2");
            result = 1;
            break;
        default:
            throw new IllegalArgumentException();
        }
        return result;
    }
 
    private static void sfDeadStroreDueToSwitchFallthroughWRONG(final int input) {
 
        int result = 0;
        switch (input) {
        case 1:
            System.out.println("   - enter case 1");
            result = 3;
            break;
        case 2:
            System.out.println("   - enter case 2");
            result = 4;
        default:
            System.out.println("   - enter default");
            // Next line should show SF_DEAD_STORE_DUE_TO_SWITCH_FALLTHROUGH 
            result = 5;
        }
        System.out.println("   - result=" + result);
    }
 
    private static void sfDeadStroreDueToSwitchFallthroughCORRECT(final int input) {
        int result = 0;
        switch (input) {
        case 0:
            System.out.println("   - enter case 0");
            result = 3;
            break;
        case 1:
            System.out.println("   - enter case 1");
        default:
            System.out.println("   - enter default");
            result = 5;
            break;
        }
        System.out.println("   - result=" + result);
    }
}
