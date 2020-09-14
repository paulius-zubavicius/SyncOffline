package com.owr.so.commons;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Storage {

	public static <T> void save(Path savePath, T payload) {

		Writer writer;
		try {
			savePath.toFile().renameTo(new File(savePath.toFile().getAbsolutePath() + "_old"));
			writer = new FileWriter(savePath.toFile(), false);
			Gson gson = new GsonBuilder().create();
			gson.toJson(payload, writer);
			writer.flush();
			writer.close();
			
			new File(savePath.toFile().getAbsolutePath() + "_old").delete();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static <T> T load(Path savePath, Class<T> clazz) {
		try {
			Reader reader = new FileReader(savePath.toFile());

			Gson gson = new GsonBuilder().create();
			T obj = gson.fromJson(reader, clazz);

			reader.close();
			return obj;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
