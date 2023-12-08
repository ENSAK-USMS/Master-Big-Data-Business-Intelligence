package com.oracle.springapp;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.oracle.springapp.model.*;
import com.oracle.springapp.remote.Remote;
import com.oracle.springapp.remote.RemoteInterface;
import com.oracle.springapp.service.JpaService;

/**
 * SpringBoot application main class. It uses JdbcTemplate class which
 * internally uses UCP for connection check-outs and check-ins.
 *
 */

@SpringBootApplication
public class OracleJdbcApplication implements CommandLineRunner {
	public static void main(String[] args) throws AccessException, RemoteException {
		SpringApplication.run(OracleJdbcApplication.class, args);
	}

	@Autowired
	JpaService<Account> accountService;
	@Autowired
	JpaService<Cashier> cashierService;
	@Autowired
	JpaService<Cooker> cookerService;
	@Autowired
	JpaService<Waiter> waiterService;
	@Autowired
	JpaService<Food> foodService;
	@Autowired
	JpaService<Orders> ordersService;
	@Autowired
	JpaService<Basket> basketService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("================Server Sides========================");
		try {
			// accountService.getAllRows().forEach(e -> {
			// 	// .info("Account  " + e.getUsername());
			// });

			Registry registry = LocateRegistry.createRegistry(5000);
			RemoteInterface<Account> account = new Remote<Account>(accountService);
			registry.rebind("account", account);
			RemoteInterface<Cashier> cashier = new Remote<Cashier>(cashierService);
			registry.rebind("cashier", cashier);
			RemoteInterface<Cooker> cooker = new Remote<Cooker>(cookerService);
			registry.rebind("cooker", cooker);
			RemoteInterface<Waiter> waiter = new Remote<Waiter>(waiterService);
			registry.rebind("waiter", waiter);
			RemoteInterface<Food> food = new Remote<Food>(foodService);
			registry.rebind("food", food);
			RemoteInterface<Orders> orders = new Remote<Orders>(ordersService);
			registry.rebind("orders", orders);
			RemoteInterface<Basket> basket = new Remote<Basket>(basketService);
			registry.rebind("basket", basket);

			System.out.println("Server is ready");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
