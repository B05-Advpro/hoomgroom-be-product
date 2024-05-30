package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionStatusUpdateRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.JwtService;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final JwtService jwtService;

    public TransactionController(TransactionService transactionService, JwtService jwtService) {
        this.transactionService = transactionService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestHeader(value = "Authorization") String token,
                                                         @RequestBody TransactionRequestDto request) {
        String newToken = token.substring(7);
        if (!jwtService.isTokenValid(newToken) || !jwtService.extractRole(newToken).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Transaction transaction = transactionService.create(request, token);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Transaction>> getAllTransaction() {
        List<Transaction> transactions = transactionService.getAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<List<Transaction>> getTransactionByusername(@RequestHeader(value = "Authorization") String token,
                                                                    @PathVariable String username) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<Transaction> transactions = transactionService.getTransactionByUsername(username);
        return ResponseEntity.ok(transactions);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @PostMapping("/next")
    public ResponseEntity<Transaction> nextStatusTransaction(@RequestBody TransactionStatusUpdateRequestDto request) {
        Transaction transaction = transactionService.nextStatus(request);
        return ResponseEntity.ok(transaction);
    }
}