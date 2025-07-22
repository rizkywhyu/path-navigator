import java.util.*;

public class PathNavigator {

    public static String navigatePath(String initialPath, List<String> commands) {
        Deque<String> pathStack = new ArrayDeque<>();

        // Split path into parts
        String[] parts = initialPath.split("\\\\");
        for (String part : parts) {
            if (!part.isEmpty()) {
                pathStack.addLast(part);
            }
        }

        for (String command : commands) {
            if (command.startsWith("cd ")) {
                String target = command.substring(3).trim();

                if (target.startsWith("/")) {
                    // Reset to root of current drive (preserve drive like D:)
                    String drive = pathStack.peekFirst();
                    pathStack.clear();
                    pathStack.addLast(drive); // Keep the drive (e.g., D:)

                    for (String dir : target.substring(1).split("/")) {
                        if (!dir.isEmpty()) {
                            pathStack.addLast(dir);
                        }
                    }
                } else {
                    // Relative path handling
                    String[] dirs = target.split("/");
                    for (String dir : dirs) {
                        if (dir.equals("..")) {
                            if (pathStack.size() > 1) { // Do not pop the drive letter
                                pathStack.removeLast();
                            }
                        } else if (!dir.equals(".") && !dir.isEmpty()) {
                            pathStack.addLast(dir);
                        }
                    }
                }
            }
        }

        // Join the final path
        return String.join("\\", pathStack);
    }

    public static void main(String[] args) {
        String initialPath = "D:\\Documents\\kerja\\belajar";
        List<String> commands = Arrays.asList(
            "cd ../..",
            "cd kerja",
            "cd ..",
            "cd",
            "cd kerja/belajar"
        );

        String finalPath = navigatePath(initialPath, commands);
        System.out.println("Final Path: " + finalPath);
    }
}
