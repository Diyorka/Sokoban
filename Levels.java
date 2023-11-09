

public class Levels {

    public Levels(){

    }



    // TODO Code for Backend Server parser
    import java.nio.charset.StandardCharsets;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.nio.file.Files;
    import java.util.List;
    private String loadLevel(String levelFileName) {
        StringBuilder data = new StringBuilder();

        try {
            Path filePath = Paths.get(levelFileName);

            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

            for (String line : lines) {
                char[] symbols = line.toCharArray();
                for (char symbol : symbols) {
                    if ('0' <= symbol && symbol <= '4') {
                        data.append(symbol);
                    }
                }
                 data.append('A');
            }
            System.out.println(data.toString());
            return data.toString();

        } catch (IOException ioe) {
            System.out.println("Error " + ioe);
        }


      return data.toString();
}
