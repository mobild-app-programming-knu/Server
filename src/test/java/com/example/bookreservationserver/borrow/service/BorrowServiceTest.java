package com.example.bookreservationserver.borrow.service;

import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import com.example.bookreservationserver.borrow.domain.service.BorrowValidator;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class BorrowServiceTest {
    @InjectMocks
    private BorrowService borrowService;

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private BorrowValidator validator;

    @Test
    @DisplayName("책 대여 성공")
    public void testBorrow(){
        //given
        final BorrowRequest borrowRequest = borrowRequest();

        doNothing().when(validator).validate(any());
        final List<BorrowBookResponse> borrowBookResponseList = borrowBookResponse();
        doReturn(borrowBookResponseList).when(borrowRepository).findBorrowBookByUserIdAndBookIdOrderByLatest(isA(Long.class), isA(Long.class));

        //when
        BorrowBookResponse borrowBookResponse = borrowService.borrowBook(borrowRequest);

        //then
        assertEquals(borrowBookResponse.getBorrow_id(), 3L);
    }

    private BorrowRequest borrowRequest(){
        return BorrowRequest.builder().borrowerId(1L).bookId(2L).build();
    }

    private List<BorrowBookResponse> borrowBookResponse(){
        return List.of(
                BorrowBookResponse.builder().borrow_id(3L).build()
        );
    }
}
