/*
 *  Copyright 2020 johnny850807 (Waterball)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package tw.waterball.vocabnotes.api.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tw.waterball.vocabnotes.api.ErrorCodes;
import tw.waterball.vocabnotes.api.ErrorResponse;
import tw.waterball.vocabnotes.api.MemberController;
import tw.waterball.vocabnotes.services.exceptions.EmailNotFoundException;
import tw.waterball.vocabnotes.services.exceptions.PasswordNotCorrectException;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@ControllerAdvice(assignableTypes = MemberController.class)
public class MemberControllerAdvice {

    @ExceptionHandler(value = EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> emailNotFound(EmailNotFoundException e){
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorResponse(ErrorCodes.Members.EMAIL_NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(value = PasswordNotCorrectException.class)
    public ResponseEntity<ErrorResponse> passwordNotCorrect(PasswordNotCorrectException e){
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorResponse(ErrorCodes.Members.PASSWORD_NOT_CORRECT, e.getMessage()));
    }

}
