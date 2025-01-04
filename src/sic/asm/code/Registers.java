package sic.asm.code;

import java.util.Map;

public class Registers {
	public static final int A = 0;
    public static final int X = 1;
    public static final int L = 2;
    public static final int B = 3;
    public static final int S = 4;
    public static final int T = 5;
    public static final int F = 6;
    // Skip 7
    public static final int PC = 8;
    public static final int SW = 9;
    
    public static final Map<String, Integer> symbolToValuesMap = Map.of(
            "A", A,
            "X", X,
            "L", L,
            "B", B,
            "S", S,
            "T", T,
            "F", F,
            "PC", PC,
            "SW", SW
            );

}
