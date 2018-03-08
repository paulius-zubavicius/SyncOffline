package com.owr.so.commons;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Storage<T> {

	public void save(Path savePath, T payload) throws IOException {

		Writer writer = new FileWriter(savePath.toFile());

		Gson gson = new GsonBuilder().create();
		gson.toJson(payload, writer);
		writer.flush();
		writer.close();
	}

	public T load(Path savePath, Class<T> clazz) throws IOException {

		Reader reader = new FileReader(savePath.toFile());

		Gson gson = new GsonBuilder().create();
		T obj = gson.fromJson(reader, clazz);

		reader.close();

		return obj;
	}

}
