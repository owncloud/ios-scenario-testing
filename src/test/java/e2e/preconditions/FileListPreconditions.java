/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.preconditions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import e2e.world.World;
import utils.LocProperties;

public class FileListPreconditions {

    private final World world;
    private final String user = LocProperties.getProperties().getProperty("userNameDefault");

    public FileListPreconditions(World world) {
        this.world = world;
    }

    public void itemsCreatedInAccount(String userName, List<Map<String, String>> rows) throws Throwable {
        for (Map<String, String> row : rows) {
            String type = row.get("type");
            String itemName = row.get("name");
            if (!world.filesAPI().itemExist(itemName, userName)) {
                switch (type) {
                    case "folder", "item" -> world.filesAPI().createFolder(itemName, userName);
                    case "file" -> world.filesAPI().pushFile(itemName, userName);
                    case "shortcut" -> world.filesAPI().pushFileByMime(itemName, "text/uri-list");
                    case "image" -> world.filesAPI().pushFileByMime(itemName, "image/jpg");
                    case "pdf" -> world.filesAPI().pushFileByMime(itemName, "application/pdf");
                    case "audio" -> world.filesAPI().pushFileByMime(itemName, "audio/mpeg3");
                    case "video" -> world.filesAPI().pushFileByMime(itemName, "video/mp4");
                    case "txt" -> world.filesAPI().pushFileByMime(itemName, "text/plain");
                    case "docx" -> world.filesAPI().pushFileByMime(itemName,
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                }
            }
        }
        world.fileListPage().refreshBySwipe();
    }

    public void folderContains(String folderName, int files) throws IOException {
        if (!world.filesAPI().itemExist(folderName, user)) {
            world.filesAPI().createFolder(folderName, user);
        }
        for (int i = 0; i < files; i++) {
            world.filesAPI().pushFile(folderName + "/file_" + i + ".txt", user);
        }
    }

    public void itemFavorited(String itemName) throws IOException {
        world.filesAPI().setFavorite(itemName);
    }
}
