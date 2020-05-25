package br.com.caelum.testes;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import br.com.caelum.JpaConfigurator;

public class TestaPool {
	
	public static void main(String[] args) throws PropertyVetoException,
	SQLException {
	
	BasicConfigurator.configure();

	ComboPooledDataSource dataSource = (ComboPooledDataSource) new JpaConfigurator()
		.getDatSource();

	for (int i = 0; i < 10; i++) {
	
		dataSource.getConnection();
	
		System.out.println(i + " - Conexões existentes: "
				+ dataSource.getNumConnections());
		System.out.println(i + " - Conexões ocupadas: "
				+ dataSource.getNumBusyConnections());
		System.out.println(i + " - Conexões ociosas: "
				+ dataSource.getNumIdleConnections());
	
		System.out.println("");
	}
	
	}
}
