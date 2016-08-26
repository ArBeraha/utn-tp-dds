package ar.edu.utn.frba.dds.util.file;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static File obtenerArchivoBusquedas() throws IOException {
        File file = new File("C:\\busquedas\\busquedas.txt");
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }
}
