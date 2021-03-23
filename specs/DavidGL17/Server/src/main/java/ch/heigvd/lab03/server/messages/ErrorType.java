/*
 * @File ErrorType.java
 * @Authors : David González León
 * @Date 20 mars 2021
 */
package ch.heigvd.lab03.server.messages;

public enum ErrorType {
   WRONG_MESSAGE("ERROR WRONG MESSAGE"), SYNTAX_ERROR("ERROR SYNTAX ERROR"), SYSTEM_ERROR("ERROR SYSTEM ERROR"),
   COMPUTATION_ERROR("ERROR COMPUTATION ERROR");

   private final String message;

   ErrorType(String message) {
      this.message = message;
   }

   public String getMessage() {
      return message;
   }

   public static ErrorType getErrorWithMessage(String message) {
      if (message.equals(WRONG_MESSAGE.getMessage())) {
         return WRONG_MESSAGE;
      }
      if (message.equals(SYNTAX_ERROR.getMessage())) {
         return SYNTAX_ERROR;
      }
      if (message.equals(SYSTEM_ERROR.getMessage())) {
         return SYSTEM_ERROR;
      }
      if (message.equals(COMPUTATION_ERROR.getMessage())) {
         return COMPUTATION_ERROR;
      }
      return SYNTAX_ERROR;
   }
}
