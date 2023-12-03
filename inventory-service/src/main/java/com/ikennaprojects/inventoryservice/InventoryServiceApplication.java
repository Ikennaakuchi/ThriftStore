package com.ikennaprojects.inventoryservice;

import com.ikennaprojects.inventoryservice.model.Inventory;
import com.ikennaprojects.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

	@Value("${inventory.dataloader.enabled}")
	private Boolean isDataLoaderEnabled;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			if(isDataLoaderEnabled && inventoryRepository.count() == 0) {
				List<Inventory> inventoryList = new ArrayList<>();

				Inventory inventory1 = new Inventory();
				inventory1.setSkuCode("Iphone-15");
				inventory1.setQuantity(2);

				Inventory inventory2 = new Inventory();
				inventory2.setSkuCode("Iphone-14");
				inventory2.setQuantity(3);

				inventoryList.add(inventory1);
				inventoryList.add(inventory2);

				inventoryRepository.saveAll(inventoryList);
			}};
	}

}
