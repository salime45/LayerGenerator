package com.imonje.layergenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author imonje
 */
public class Main {

    public static void main(String args[]) {

        try {
            File dirModels = new File("models");
            File dirTemplates = new File("templates");
            File dirRes = new File("result");
            File dirResModels = new File("result" + File.separator + "model");

            //delete previus data
            FileUtils.deleteFolder(dirRes);
            
            //create new folders
            FileUtils.createDir(dirModels);
            FileUtils.createDir(dirTemplates);
            FileUtils.createDir(dirRes);
            FileUtils.createDir(dirResModels);
            
            //Copy templates
            FileUtils.copyContent(dirTemplates, dirRes);

            //Get models names
            File[] listOfModels = dirModels.listFiles();
            ArrayList<String> nameModels = new ArrayList<>();

            for (File f : listOfModels) {
                nameModels.add(FileUtils.removeExtension(f.getName()));

            }

            //process templates
            processFile(dirRes, nameModels);
            
            //copy models a result
            FileUtils.copyDirectory(dirModels, dirResModels);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void processFile(File f, ArrayList<String> listOfModels) throws IOException {

        File[] files = f.listFiles();
        for (File aux : files) {
            if (aux.isDirectory()) {
                processFile(aux, listOfModels);
            } else if (aux.getName().contains("@MODEL@")) {

                //Get content file
                String content = FileUtils.readFile(aux.getAbsolutePath(), StandardCharsets.UTF_8);

                for (String model : listOfModels) {

                    String pathNewFile = aux.getParent() + File.separator + aux.getName().replace("@MODEL@", model);

                    File newFile = new File(pathNewFile);
                    try (BufferedWriter output = new BufferedWriter(new FileWriter(newFile))) {
                        output.write(content.replace("@MODEL@", model));
                    }
                }

                aux.delete();
            }
        }
    }

}
