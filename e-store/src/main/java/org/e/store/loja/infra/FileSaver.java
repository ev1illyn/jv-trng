package org.e.store.loja.infra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;

import javax.servlet.http.Part;

public class FileSaver {

	public static final String SERVER_PATH = "/e-store";

	/**
	 * método que salva o arquivo e o caminho relativo para salvar a imagem e
	 * retorna o caminho relativo usado para salvar concatenando com o nome do
	 * arquivo
	 */
	public String write(Part arquivo, String path) {
		String relativePath = path + "/" + arquivo.getSubmittedFileName();
		try {
			arquivo.write(SERVER_PATH + "/" + relativePath);
			return relativePath;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Método que transfere o arquivo do servidor para a response com o canal de entrada e saída(input e output)
	 */
	public static void transfer(Path source, OutputStream outputStream) {
		try {
			FileInputStream input = new FileInputStream(source.toFile());
			
			// try-with-resources
			try (ReadableByteChannel inputChannel = Channels.newChannel(input);
					WritableByteChannel outputChannel = Channels.newChannel(outputStream);) {
				
				// a transferência é feita por meio de um Buffer
				ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);
				
				// leitura do canal de entrada e transferência para o buffer enquanto existir bytes a serem lidos
				// escrita no canal de saída
				while(inputChannel.read(buffer) != -1) {
					buffer.flip();
					outputChannel.write(buffer);
					buffer.clear();
				}
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

	}
}
