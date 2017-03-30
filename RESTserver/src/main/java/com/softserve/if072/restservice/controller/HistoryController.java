package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.common.model.dto.HistorySearchDTO;
import com.softserve.if072.restservice.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The HistoryController class handles requests for cart resources
 *
 * @author Igor Kryviuk
 */
@RestController
@RequestMapping("api/users/{userId}/histories")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * Handles requests for search history records by given criterias
     *
     * @param userId     - current user unique identifier
     * @param pageNumber - number of page
     * @param pageSize   - number of records per page
     * @param searchData DTO that contains search params
     * @return list of found cart records or empty list
     */
    @PreAuthorize("#userId == authentication.user.id")
    @PostMapping("/search/{pageNumber}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public Page<History> postSearchForm(@PathVariable int userId, @PathVariable int pageNumber,
                                        @PathVariable int pageSize, @RequestBody HistorySearchDTO searchData) {
        Pageable pageable = new PageRequest(pageNumber - 1, pageSize);
        return historyService.getByUserIdAndSearchParams(userId, searchData, pageable);
    }

    /**
     * Handles requests for deleting a record from the history of current user
     *
     * @param historyId - history unique identifier
     */
    @PreAuthorize("@historySecurityService.hasPermissionToDelete(#historyId)")
    @DeleteMapping("/{historyId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable int historyId) {
        historyService.delete(historyId);
    }

    /**
     * Handles requests for deleting all records from the history of current user
     */
    @PreAuthorize("#userId == authentication.user.id")
    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteAll(@PathVariable int userId) {
        historyService.deleteAll(userId);
    }

    @PreAuthorize("@historySecurityService.hasPermissionToAccessByProductId(#productId)")
    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<History> getByProductId(@PathVariable int userId, @PathVariable int productId) {

        return historyService.getByProductId(userId, productId);
    }

    @PreAuthorize("#history != null && #history.user != null && #history.user.id == authentication.user.id")
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody HistoryDTO historyDTO) {
        historyService.insert(historyDTO);
    }

    @PreAuthorize("#history != null && #history.user != null && #history.user.id == authentication.user.id")
    @PutMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody HistoryDTO historyDTO) {
        historyService.update(historyDTO);
    }
}