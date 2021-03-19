# Simple calculator protocol

This document uses keywords indicating requirement level as described in
[RFC 2119](rfc-2119).

[rfc-2119]: https://tools.ietf.org/html/rfc2119

This document defines a remote calculator protocol using [JSON-RPC 2.0][jsonrpc]
over TCP.

[jsonrpc]: https://www.jsonrpc.org/specification

The server SHOULD listen on TCP port 58371 where clients may connect to.
Clients SHOULD be able to connect to any address provided at use-time, and no
addresses are provided by this document.

Clients MUST only send JSON-RPC data over the TCP connection.
If anything other than [JSON-RPC 2.0][jsonrpc] is received by either end the
connection MUST be immediately closed.

Upon establishing a TCP connection, clients MAY invoke any of the following
commands:

- `float add(float, float)`: Adds two IEEE-754 numbers.
- `string addBig(string, string)`: Adds two arbitrary-sized floating-point or
  integer numbers. Integer numbers MUST NOT include a decimal point or digits.
- `float multiply(float, float)`: Multiplies two IEEE-754 numbers.
- `string multiplyBig(string, string)`: Multiplies two arbitrary-sized
  floating-point or integer numbers.
  Integer numbers MUST NOT include a decimal point or digits.
- `bool isPrime(int)`: Returns whether the specified number is prime.

The server MUST respond using the [JSON-RPC 2.0][jsonrpc] semantics for response
and error.
No additional error codes to the pre-defined errors are defined.

Clients MAY disconnect at any moment.
The server should be able to handle multiple clients connecting at once.
