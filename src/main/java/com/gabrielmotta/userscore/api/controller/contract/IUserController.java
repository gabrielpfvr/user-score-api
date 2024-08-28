package com.gabrielmotta.userscore.api.controller.contract;

import com.gabrielmotta.userscore.api.dto.CustomPageRequest;
import com.gabrielmotta.userscore.api.dto.UserFilter;
import com.gabrielmotta.userscore.api.dto.UserRequest;
import com.gabrielmotta.userscore.api.dto.UserResponse;
import com.gabrielmotta.userscore.api.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.web.PagedModel;

public interface IUserController {


    @Operation(summary = "User creation. Admin role required.",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content)
        }
    )
    void create(UserRequest dto);

    @Operation(summary = "Retrieve all users based on filters and page request.",
        responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "401", content = @Content)
        }
    )
    PagedModel<UserResponse> getAllPaged(@ParameterObject UserFilter userFilter, @ParameterObject CustomPageRequest pageable);

    @Operation(summary = "Retrieve user details by id",
        responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(
                responseCode = "404",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
        }
    )
    UserResponse getById(Long id);

    @Operation(summary = "Update user. Admin role required.",
        responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(
                responseCode = "404",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
        }
    )
    void update(Long id, UserRequest dto);

    @Operation(summary = "Soft delete user. Admin role required.",
        responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(
                responseCode = "404",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
        }
    )
    void delete(Long id);

    @Operation(summary = "Activate soft-deleted users. Admin role required.",
        responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(
                responseCode = "404",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
        }
    )
    void activateUser(Long id);

    @Operation(summary = "Retrieve user score description by id.",
        responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(
                responseCode = "404",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
        }
    )
    String getUserScoreDescription(Long id);
}
