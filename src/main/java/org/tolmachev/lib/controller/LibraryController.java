package org.tolmachev.lib.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tolmachev.lib.model.Subscription;
import org.tolmachev.lib.model.UploadRequest;
import org.tolmachev.lib.service.LibraryService;

@RestController
@RequestMapping(path = "/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @Operation(summary = "Загрузить данные по абонементам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные добавлены"),
            @ApiResponse(responseCode = "400", description = "Имеется ошибка в отправленных пользователем данных")
    })
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    void upload(@Valid @RequestBody UploadRequest uploadRequest){
        libraryService.saveOldData(uploadRequest);
    }

    @Operation(summary = "Поиск абонемента по фамилии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Абонемент найден"),
            @ApiResponse(responseCode = "404", description = "Абонемент не найден")
    })
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Subscription search(@RequestParam(name = "userFullName") String userFullName) {
        return libraryService.getSubscription(userFullName);
    }
}
