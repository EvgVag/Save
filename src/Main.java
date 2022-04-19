import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGame(String adress, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(adress);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String adress, List<String> files) {
        int i = 0;
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(adress))) {
            for (String s : files) {
                try (FileInputStream fis = new FileInputStream(s)) {
                    i++;
                    ZipEntry entry = new ZipEntry("save" + i + ".dat");
                    zout.putNextEntry(entry);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    zout.write(bytes);
                    zout.closeEntry();
                }
                File file = new File(s);
                file.delete();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 3, 5, 10);
        GameProgress gameProgress2 = new GameProgress(80, 4, 6, 11);
        GameProgress gameProgress3 = new GameProgress(150, 7, 9, 12);
        saveGame("C:\\Users\\evgen\\Desktop\\Games\\savegames\\save1.dat", gameProgress1);
        saveGame("C:\\Users\\evgen\\Desktop\\Games\\savegames\\save2.dat", gameProgress2);
        saveGame("C:\\Users\\evgen\\Desktop\\Games\\savegames\\save3.dat", gameProgress3);
        zipFiles("C:\\Users\\evgen\\Desktop\\Games\\savegames\\zip.zip",
                Arrays.asList("C:\\Users\\evgen\\Desktop\\Games\\savegames\\save1.dat",
                        "C:\\Users\\evgen\\Desktop\\Games\\savegames\\save2.dat",
                        "C:\\Users\\evgen\\Desktop\\Games\\savegames\\save3.dat"));
    }
}
