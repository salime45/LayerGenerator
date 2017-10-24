package com.imonje.layergenerator;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {

    public static void createDir(File f) {
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    public static void copyFile(File source, File target) {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Copia todo el contenido de la carpeta <code>orig_folder</code> a la
     * carpeta <code>dest_folder</code>
     *
     * @param orig_folder - Ruta origen
     * @param dest_folder - Ruta destino
     * @return devuelve true si se copia correctamente, false en caso contrario
     */
    public static boolean copyContent(File orig_folder, File dest_folder) {
        //Comprobamos que exista la ruta de origen
        if (!orig_folder.exists()) {
            return false;
        }
        //Creamos la ruta destino
        dest_folder.mkdirs();
        if (!dest_folder.exists()) {
            return false;
        }

        //Obtenemos el contenido de la ruta de origen
        File content[] = orig_folder.listFiles();
        for (int i = 0; i < content.length; i++) {

            File ini = content[i];
            File fin = new File(dest_folder.getAbsolutePath() + File.separator + ini.getName());
            if (ini.isDirectory()) {
                //Si es un directorio, lo creamos y movemos el contenido
                fin.mkdirs();
                copyContent(ini, fin);
            } else {
                //Si es un fichero, lo copiamos tal cual
                FileUtils.copyFile(ini, fin);
            }
        }

        return true;
    }

    /**
     * Copia todo el contenido de la carpeta <code>orig_folder</code> a la
     * carpeta <code>dest_folder</code>
     *
     * @param source - Ruta origen
     * @param destiny - Ruta destino
     * @return devuelve true si se copia correctamente, false en caso contrario
     */
    public static boolean copyContent(String source, String destiny) {
        return copyContent(new File(source), new File(destiny));
    }

    /**
     * Obtiene el nombre de un fichero sin la extensión
     *
     * @param filename String con el nombre del fichero a analizar
     * @return El nombre del fichero sin extensión
     */
    public static String removeExtension(String filename) {
        int pos = filename.lastIndexOf(".");
        if (pos == -1) {
            return filename;
        }
        return filename.substring(0, pos);
    }

    /**
     * Devuelve la extensión de un fichero
     *
     * @param filename String con el nombre del fichero a analizar
     * @return Extensión del fichero (sin el 'punto'), o una cadena vacía si no
     * se ha podido obtener la extensión.
     */
    public static String getExtension(String filename) {
        int pos = filename.lastIndexOf(".");
        if ((pos == -1) || (pos == (filename.length() - 1))) {
            return "";
        }
        return filename.substring(pos + 1, filename.length());
    }

    /**
     * Método para cerrar un descriptor de fichero
     *
     * @param closeable Elemento a cerrar
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Ejecuta un programa externo llamando al sistema operativo.
     *
     * @param command Cadena con el comando a ejecutar
     * @return <code>true</code> si la ejecución es correcta, <code>false</code>
     * si se produce algún error
     */
    public static boolean executeCommand(String command) {
        try {
            System.out.println("Ejecutar: " + command);
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linea = null;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            if (br.ready()) {
                while ((linea = br.readLine()) != null) {
                    System.err.println(linea);
                }
            }
            return (process.waitFor() == 0);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    /**
     * Duplica todas las barras ('/','\') transformándolas en una barra doble
     * ('\\'). Para filtrar nombres de archivo en llamadas a la utilidad
     * exiftool.
     *
     * @param str Cadena a filtrar
     * @return Cadena filtrada con barras dobles.
     */
    public static String doubleSlashes(String str) {
        return str.replace("/", "\\").replace("\\", "\\\\");
    }

}
