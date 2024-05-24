package com.todimu.backend.bookstoreservice.utils;

import com.todimu.backend.bookstoreservice.data.dto.response.BaseResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Utils {

    private static final Integer PAGE_NUMBER = 0;
    private static final Integer PAGE_SIZE = 15;
    private static final String SUCCESS_MESSAGE = "successful";

    public static Pageable createSortedPageableObject(Integer pageNumber, Integer pageSize) {
        Pageable pagingAndSortedByCreatedDateDesc;

        if (pageNumber == null || pageSize == null) {
            pagingAndSortedByCreatedDateDesc = PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.by("createdDate").descending());
        } else {
            pagingAndSortedByCreatedDateDesc = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        }
        return  pagingAndSortedByCreatedDateDesc;
    }

    public static BaseResponse createBseResponse(Object data) {
        return new BaseResponse(data, SUCCESS_MESSAGE, false);
    }
}
