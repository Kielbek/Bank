package com.example.bankspring2.User;

import com.example.bankspring2.Enum.TypeOfTransaction;
import com.example.bankspring2.Function.Function;
import com.example.bankspring2.Transaction.Transaction;
import com.example.bankspring2.Transaction.TransactionRepository;
import com.example.bankspring2.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserRepository userRepsitory;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TokenService tokenService;

    @PostMapping("/users")
    public void addUser(@RequestBody UserDTO user) {
        Optional<User> checkMail = userRepsitory.findByEmail(user.getEmail());
        if (checkMail.isPresent()) {
            throw new RuntimeException("Email zajety!");
        }
        User user1 = new User(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getPin(), user.getRoles());
        userRepsitory.save(user1);
    }

    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userRepsitory.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable long id) {
        Optional<User> user = userRepsitory.findById(id);
        Function.checkOptionalIsEmpty(user);
        return user.get();
    }

    @GetMapping("/users/registration/{login}/{pin}")
    public String loginUser(@PathVariable String login, @PathVariable int pin) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String hashPin = Function.hashPin(pin);
        Optional<User> user = userRepsitory.findByLoginAndPin(login, hashPin);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found! Bad email or password!");
        }
        return tokenService.createToken(user.get().getId());
    }

    @PutMapping("/user/deposit/{login}/{pin}/{amount}")
    public void makeDeposit(@PathVariable String login, @PathVariable int pin, @PathVariable int amount) {
        Function.checkAmount(amount);
        Optional<User> user = userRepsitory.findByLoginAndPin(login, Function.hashPin(pin));
        Function.checkOptionalIsEmpty(user);
        Function.checkAccountStatus(user.get());

        user.get().setAccountBalance(user.get().getAccountBalance() + amount);
        Transaction transaction = new Transaction(user.get().getId(), user.get().getId(), amount, TypeOfTransaction.DEPOSIT);
        transactionRepository.save(transaction);
        user.get().getTransactions().add(transaction);
        userRepsitory.save(user.get());
    }

    @PutMapping("/user/withdrawal/{login}/{pin}/{amount}")
    public void makeWithdrawal(@PathVariable String login, @PathVariable int pin, @PathVariable int amount) {
        Optional<User> user = userRepsitory.findByLoginAndPin(login, Function.hashPin(pin));
        Function.checkOptionalIsEmpty(user);
        Function.checkAccountStatus(user.get());

        Function.checkAmount(user.get(), amount);
        user.get().setAccountBalance(user.get().getAccountBalance() - amount);
        Transaction transaction = new Transaction(user.get().getId(), user.get().getId(), amount, TypeOfTransaction.WITHDRAWAL);
        transactionRepository.save(transaction);
        user.get().getTransactions().add(transaction);
        userRepsitory.save(user.get());
    }

    @PutMapping("/user/transfer/{login}/{pin}/{amount}/{recipientNumber}")
    public void makeTransfer(@PathVariable String login, @PathVariable int pin, @PathVariable int amount, @PathVariable int recipientNumber) {
        Optional<User> sender = userRepsitory.findByLoginAndPin(login, Function.hashPin(pin));
        Optional<User> recipient = userRepsitory.findByCountNumber(recipientNumber);
        Function.checkOptionalIsEmpty(sender);
        Function.checkOptionalIsEmpty(recipient);
        Function.checkAccountStatus(sender.get());
        Function.checkAccountStatus(recipient.get());

        Function.checkAmount(sender.get(), amount);
        sender.get().setAccountBalance(sender.get().getAccountBalance() - amount);
        recipient.get().setAccountBalance(recipient.get().getAccountBalance() + amount);
        Transaction transaction = new Transaction(sender.get().getId(), recipient.get().getId(), amount, TypeOfTransaction.TRANSFER);
        transactionRepository.save(transaction);
        sender.get().getTransactions().add(transaction);
        Transaction transaction2 = new Transaction(sender.get().getId(), recipient.get().getId(), amount, TypeOfTransaction.TRANSFER);
        transactionRepository.save(transaction2);
        recipient.get().getTransactions().add(transaction2);
        userRepsitory.save(sender.get());
        userRepsitory.save(recipient.get());
    }

    @PutMapping("/admin/status/{userId}")
    public void changeAccountStatus(@PathVariable long userId) {
        Optional<User> user = userRepsitory.findById(userId);
        Function.checkOptionalIsEmpty(user);
        user.get().setStatus(!user.get().getStatus());
        userRepsitory.save(user.get());
    }


}
