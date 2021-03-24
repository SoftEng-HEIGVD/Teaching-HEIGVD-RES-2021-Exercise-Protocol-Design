# CALC protocol

## Objective

Specify a protocol to allow a client to send calculation requests to a server. A server must at least implement additions (ADD) and multiplications (MUL) and result reuse (RR). A server may implement any other of the operations presented, but must always implement result reuse for the operations it implements unless specified otherwise in the given description of the command. Server must implement operations to support integer operands and may support floating-point operands

## Transport

The protocol uses TCP on port 2781 (0xADD). IP address should be known by the client as it depends on the person implementing the server. The protocol is stateless and doesn't keep results of precedent requests

## Communication

The client speaks first and can send a calculation request or an information request. Commands are given in all caps but the protocol is actually case-insensitive (CALC, calc and all case-variants have the same effect). Requests may contains multiple commands separated by `;`, parameters of commands are separated by simple spaces and a request is terminated by a line-return. A request may only be of one type (INFO, COMP, ERROR,...) 

### Client

* INFO: Starts an information request
  * OP: Asks for the list of operations
  * FLOAT: Asks for the list of operations that support floating-point operands and results
* COMP: Starts a computation request
  * ADD: Adds its operands. Minimum two operands
  * MUL: Multiplies its operands, Minimum two operands
  * SUB: Substracts its operands from left to right. Minimum two operands
  * DIV: Divides its operands from left to right. Only supports two operands
  * MOD: Computes the modulus of its operands. Only supports integers. Only supports two operands. RR unusable with this operation
  * SQRT: Computes the square root of its operand. Only supports one operand
  * POW: Computes the power with its first operand as the base and its second operand as the exponent. Only supports two operands
* EXIT: Closes the connection

#### Multiple parameters

Unless specified otherwise, commands must be able to apply the operation to more than two operands

#### Result reuse

An operand for an operation may be the result of a precedent operation in the same request. It is specified as RX where X is the number of the operation in the request starting from 0. 

### Server

* INFO: Response to an information request. Informations requested are supplied in the order of the request
* RES: Response to a computation request. Results of multiple requested operations are separated by a single space and are returned in the same order as the request
* ERROR: Specifies the error code of the error that occured

#### Error codes

* 100: Unknown request
* 200: Missing operand
* 201: Too many operands
* 202: Result reuse operand invalid
* 203: Unsupported floating-point operand
* 300: Operation overflowed