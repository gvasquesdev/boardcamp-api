package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
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
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cliente não encontrado com o ID fornecido: " + dto.getCustomerId()));

        GameModel game = gameRepository.findById(dto.getGameId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Jogo não encontrado com o ID fornecido: " + dto.getGameId()));

        int rentedStock = rentalRepository.countByGame(game);
        int availableStock = game.getStockTotal();
        if (rentedStock >= availableStock) {
            throw new IllegalStateException("Não há estoque disponível para o jogo com ID: " + dto.getGameId());
        }

        // Calcular o preço original
        int originalPrice = (int) (dto.getDaysRented() * game.getPricePerDay());

        // Criar o objeto de aluguel
        RentalModel rental = new RentalModel(dto, customer, game, originalPrice);

        // Salvar o aluguel
        RentalModel savedRental = rentalRepository.save(rental);

        // Atualizar o estoque do jogo
        game.setStockTotal(availableStock - 1); // Reduzir o estoque em 1 unidade
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
                () -> new IllegalArgumentException("Rental not found by this gameId!"));

        if (rental.getReturnDate() != null) {
            throw new IllegalArgumentException("This rent has already been refunded");
        }

        int delayFee = calculateDelayFee(rental.getRentDate(), rental.getDaysRented(), rental.getOriginalPrice());

        RentalModel newRental = new RentalModel(rental, delayFee);
        newRental.setId(id);
        return rentalRepository.save(newRental);
    }

}
