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

	@Override
	public void run(String... args) throws Exception {
		System.out.println("================Server Sides========================");
		try {

			Registry registry = LocateRegistry.createRegistry(5000);
			RemoteInterface<Account> account = new Remote<Account>(accountService);
			registry.rebind("account", account);

			System.out.println("Server is ready");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
