package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.ExceptionNotFound;
import com.boardcamp.api.exceptions.ExceptionUnprocessableEntity;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@Service
public class RentalService {
    CustomerRepository customerRepository;
    final GameRepository gameRepository;
    final RentalRepository rentalRepository;

    RentalService(CustomerRepository customerRepository,
            GameRepository gameRepository,
            RentalRepository rentalRepository) {
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<RentalModel> findAll() {
        return rentalRepository.findAll();
    }

    public RentalModel save(RentalDTO dto) {

        CustomerModel customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ExceptionNotFound("User not found!"));
        GameModel game = gameRepository.findById(dto.getGameId())
                .orElseThrow(() -> new ExceptionNotFound("Game not found!"));

        int rentedStock = rentalRepository.countByGame(game);
        int availableStock = game.getStockTotal();

        if (rentedStock < 1) {
            throw new ExceptionUnprocessableEntity("Game out of stock!");
        }

        int originalPrice = (int) (dto.getDaysRented() * game.getPricePerDay());

        RentalModel rental = new RentalModel(dto, customer, game, originalPrice);

        RentalModel savedRental = rentalRepository.save(rental);

        game.setStockTotal(availableStock - 1);
        gameRepository.save(game);

        return savedRental;
    }

    private int calculateDelayFee(LocalDate rentDate, int daysRented, int pricePerDay) {
        LocalDate currentDate = LocalDate.now();
        Long periodRental = ChronoUnit.DAYS.between(rentDate, currentDate);

        if (periodRental <= daysRented) {
            return 0;
        } else {
            return (int) ((periodRental - daysRented) * pricePerDay);
        }
    }

    public RentalModel update(Long id) {
        RentalModel rental = rentalRepository.findById(id).orElseThrow(
                () -> new ExceptionNotFound("Rental not found by this gameId!"));

        if (rental.getReturnDate() != null) {
            throw new ExceptionUnprocessableEntity("Game already returned!");
        }

        int delayFee = calculateDelayFee(rental.getRentDate(), rental.getDaysRented(), rental.getOriginalPrice());

        RentalModel newRental = new RentalModel(rental, delayFee);
        newRental.setId(id);
        return rentalRepository.save(newRental);
    }

}
