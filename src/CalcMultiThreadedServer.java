import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

class CalcMultiThreadedServer {
    private final static int PORT = 3101;

    public static void main(String[] args) {
        new CalcMultiThreadedServer().serveClients();
    }

    public CalcMultiThreadedServer() {

    }

    public void serveClients() {
        new Thread(new ReceptionistWorker()).start();
    }

    private class ReceptionistWorker implements Runnable {
        @Override
        public void run() {
            try (ServerSocket localSocket = new ServerSocket(PORT)) {
                while (true) {
                    try {
                        Socket remoteSocket = localSocket.accept();
                        new Thread(new ServantWorker(remoteSocket)).start();
                    } catch (IOException e) {

                    }
                }
            } catch (IOException e) {
                return;
            }
        }
    }

    private enum Error {
        INVALID_NUMBER_OPERANDS, INVALID_OPERAND_FORMAT, INVALID_OPERATION, MISSING_OPERATOR, UNKNOWN_OPERATOR,
        UNKNOWN_OPERATION
    };

    private class ServantWorker implements Runnable {
        private Socket remoteSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ServantWorker(Socket remoteSocket) {
            try {
                this.remoteSocket = remoteSocket;
                in = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
                out = new PrintWriter(remoteSocket.getOutputStream());
            } catch (IOException e) {

            }
        }

        @Override
        public void run() {
            String line;
            boolean shouldRun = true;

            out.print("WELCOME\r\n- AVAILABLE OPERATIONS\r\n");

            out.print("- ADD 2\r\n");
            out.print("- MULT 2\r\n");
            out.print("- DIV 2\r\n");

            out.print("- END OPERATIONS\r\n");
            out.flush();

            try {
                while (shouldRun && (line = in.readLine()) != null) {
                    String[] parts = line.split(" ");

                    switch (parts[0]) {
                    case "COMPUTE":
                        if (parts.length > 1) {
                            compute(Arrays.copyOfRange(parts, 1, parts.length));
                        } else {
                            error(Error.MISSING_OPERATOR);
                        }
                        break;
                    case "QUIT":
                        shouldRun = false;
                        break;
                    default:
                        error(Error.UNKNOWN_OPERATION);
                    }

                    out.flush();
                }

                remoteSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e2) {

                    }
                }

                if (out != null) {
                    out.close();
                }

                if (remoteSocket != null) {
                    try {
                        remoteSocket.close();
                    } catch (IOException e2) {

                    }
                }
            }
        }

        void compute(String[] arguments) {
            switch (arguments[0]) {
            case "ADD":
                if (arguments.length == 3) {
                    try {
                        int lhs = Integer.parseInt(arguments[1]);
                        int rhs = Integer.parseInt(arguments[2]);

                        result(lhs + rhs);
                    } catch (NumberFormatException e) {
                        error(Error.INVALID_OPERAND_FORMAT);
                    }
                } else {
                    error(Error.INVALID_NUMBER_OPERANDS);
                }
                break;

            case "MULT":
                if (arguments.length == 3) {
                    try {
                        int lhs = Integer.parseInt(arguments[1]);
                        int rhs = Integer.parseInt(arguments[2]);

                        result(lhs * rhs);
                    } catch (NumberFormatException e) {
                        error(Error.INVALID_OPERAND_FORMAT);
                    }
                } else {
                    error(Error.INVALID_NUMBER_OPERANDS);
                }
                break;

            case "DIV":
                if (arguments.length == 3) {
                    try {
                        int lhs = Integer.parseInt(arguments[1]);
                        int rhs = Integer.parseInt(arguments[2]);

                        if (lhs != 0) {
                            result(lhs / rhs);
                        } else {
                            error(Error.INVALID_OPERATION);
                        }
                    } catch (NumberFormatException e) {
                        error(Error.INVALID_OPERAND_FORMAT);
                    }
                } else {
                    error(Error.INVALID_NUMBER_OPERANDS);
                }
                break;

            default:
                error(Error.UNKNOWN_OPERATOR);
            }
        }

        void error(Error error) {
            out.print("ERROR ");

            switch (error) {
            case INVALID_NUMBER_OPERANDS:
                out.print("310 INVALID NUMBER OPERANDS");
                break;
            case INVALID_OPERAND_FORMAT:
                out.print("320 INVALID OPERAND FORMAT");
                break;
            case INVALID_OPERATION:
                out.print("330 INVALID OPERATION");
                break;
            case MISSING_OPERATOR:
                out.print("340 MISSING OPERATOR");
                break;
            case UNKNOWN_OPERATOR:
                out.print("350 UNKNOWN OPERATOR");
                break;
            case UNKNOWN_OPERATION:
                out.print("300 UNKNOWN OPERATION");
                break;
            }

            out.print("\r\n");
        }

        void result(int result) {
            out.print("RESULT " + result + "\r\n");
        }
    }
}