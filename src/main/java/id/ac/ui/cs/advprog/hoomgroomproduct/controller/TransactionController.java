package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.JwtService;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestHeader(value = "Authorization") String token,
                                                         @RequestBody TransactionRequestDto request) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Transaction transaction = transactionService.create(request);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Transaction>> getAllTransaction() {
        List<Transaction> transactions = transactionService.getAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionByUserId(@RequestHeader(value = "Authorization") String token,
                                                                    @PathVariable Long userId) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        List<Transaction> transactions = transactionService.getTransactionByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}