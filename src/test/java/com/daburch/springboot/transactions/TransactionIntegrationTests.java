package com.daburch.springboot.transactions;

import com.daburch.springboot.transactions.dao.TransactionRepository;
import com.daburch.springboot.transactions.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean(name="database")
    private TransactionRepository mockRepository;

    private static ObjectMapper mapper;
    private static Transaction testTransaction1;
    private static Transaction testTransaction2;

    @Before
    public void init() {
        testTransaction1 = new Transaction();
        testTransaction1.setId(1L);
        testTransaction1.setDescription("First Transaction");
        testTransaction1.setAmount((float) 420.69);
        testTransaction1.setCategory("MISC");

        Calendar c = Calendar.getInstance();
        c.set(2020, Calendar.JANUARY, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        testTransaction1.setDate(c.getTime());

        testTransaction2 = new Transaction();
        testTransaction2.setId(2L);
        testTransaction2.setDescription("Second Transaction");
        testTransaction2.setAmount((float) 120000.00);
        testTransaction2.setCategory("OTHER");

        Calendar c2 = Calendar.getInstance();
        c2.set(2020, Calendar.MARCH, 10, 0, 0, 0);
        c2.set(Calendar.MILLISECOND, 0);
        testTransaction2.setDate(c2.getTime());

        when(mockRepository.findById(1L)).thenReturn(Optional.of(testTransaction1));
        when(mockRepository.findById(2L)).thenReturn(Optional.of(testTransaction2));
        when(mockRepository.findAll()).thenReturn(Arrays.asList(testTransaction1, testTransaction2));
        when(mockRepository.save(any(Transaction.class))).thenAnswer((Answer<Transaction>) invocation -> {
            Object[] args = invocation.getArguments();
            Transaction transaction = (Transaction) args[0];
            transaction.setId(1L);
            return transaction;
        });
        doNothing().when(mockRepository).deleteById(any(Long.class));

        mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSSZ"));
        mapper.getDateFormat().setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    @DisplayName("getAllTransactions test")
    public void testGetAllTransactions() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/transaction", String.class);

        String expectedResponse = mapper.writeValueAsString(Arrays.asList(testTransaction1, testTransaction2));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(mockRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("createTransaction test")
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDescription("First Transaction");
        transaction.setAmount((float) 420.69);
        transaction.setCategory("MISC");

        Calendar c = Calendar.getInstance();
        c.set(2020, Calendar.JANUARY, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        transaction.setDate(c.getTime());

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/transaction", transaction, String.class);

        transaction.setId(1L);
        String expectedResponse = mapper.writeValueAsString(transaction);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(mockRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("readTransaction test")
    public void testReadTransaction() throws Exception {
        ResponseEntity<String> response1 = restTemplate.postForEntity("/api/v1/transaction/read/1", "", String.class);

        String expectedResponse1 = mapper.writeValueAsString(testTransaction1);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(expectedResponse1, response1.getBody());

        ResponseEntity<String> response2 = restTemplate.postForEntity("/api/v1/transaction/read/2", "", String.class);

        String expectedResponse2 = mapper.writeValueAsString(testTransaction2);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(expectedResponse2, response2.getBody());

        verify(mockRepository, times(1)).findById(1L);
        verify(mockRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("updateTransaction test")
    public void testUpdateTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDescription("Updated First Transaction");
        transaction.setAmount((float) 100.99);
        transaction.setCategory("OTHER");

        Calendar c = Calendar.getInstance();
        c.set(2020, Calendar.FEBRUARY, 29, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        transaction.setDate(c.getTime());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(transaction), headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/transaction/1", HttpMethod.PUT, entity, String.class);

        transaction.setId(1L);
        String expectedResponse = mapper.writeValueAsString(transaction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(mockRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("deleteTransaction test")
    public void testDeleteTransaction() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/transaction/1", HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(mockRepository, times(1)).deleteById(1L);
    }
}
