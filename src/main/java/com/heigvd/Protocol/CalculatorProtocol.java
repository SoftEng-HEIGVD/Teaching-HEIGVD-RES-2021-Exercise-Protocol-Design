package com.heigvd.Protocol;

public class CalculatorProtocol {
    public final static int DEFAULT_PORT = 2222;

    public final static String CMD_CONNECTION = "CONNECTION";
    public final static String CMD_CALC = "CALC";
    public final static String CMD_RESULT = "RESULT";
    public final static String CMD_ERROR_UNKNOWN = "ERROR command unknown";
    public final static String CMD_ERROR_BAD = "ERROR bad syntax";
    public final static String CMD_ADD = "ADD";
    public final static String CMD_MULT = "MULT";
    public final static String CMD_QUIT = "QUIT";

    public final static String CMD_END = "END";

    public final static String RESPONSE_START = CMD_CONNECTION+" - Welcome " + CMD_END;
    public final static String RESPONSE_START2 = "Available: "+CMD_ADD+ " " + CMD_MULT + " " + CMD_END;
    public final static String[] SUPPORTED_COMMANDS = new String[]{CMD_CALC,CMD_ADD,CMD_MULT,CMD_QUIT};
}
