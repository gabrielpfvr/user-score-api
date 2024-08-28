package com.gabrielmotta.userscore.api.controller.contract;

import com.gabrielmotta.userscore.api.dto.LoginRequest;
import com.gabrielmotta.userscore.api.dto.LoginResponse;
import com.gabrielmotta.userscore.api.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface ILoginController {

    @Operation(summary = "User login for retrieving JWT. No auth needed.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized - User is deactivated or invalid credentials",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
        }
    )
    LoginResponse login(LoginRequest loginRequest);
}
