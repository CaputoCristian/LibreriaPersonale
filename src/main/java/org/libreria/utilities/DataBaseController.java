package org.libreria.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.libreria.libraries.Library;

import java.io.File;
import java.io.IOException;

public class DataBaseController {

    public static void saveLibrary(Library lib, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), lib);
    }

    public static Library loadLibrary(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filename), Library.class);
    }

}
