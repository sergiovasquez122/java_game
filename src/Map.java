import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Map class - Representation of a map
 *
 * @author Sergio Vasquez
 */
public class Map {
    /**
     * contains the 'interest' events of the map
     */
    private char[][] map;
    /**
     * flags the tell whether a part of the map has yet to be revealed
     */
    private boolean[][] revealed;

    /**
     * Constructor - Constructs the map and loads the first level
     */
    public Map() {
        final int MAP_SIZE = 5;
        map = new char[MAP_SIZE][MAP_SIZE];
        revealed = new boolean[MAP_SIZE][MAP_SIZE];
        loadMap(1);
    }

    public static void main(String[] args) {
        /* Test if the initial state of the first map is correct */
        Map map = new Map();
        map.displayMap(map.findStart());
        System.out.println();
        /* Reveal entire map and then display it */
        final int MAP_SIZE = 5;
        for (int i = 0; i < MAP_SIZE; ++i) {
            for (int j = 0; j < MAP_SIZE; ++j) {
                map.reveal(new Point(i, j));
            }
        }
        map.displayMap(map.findStart());
        System.out.println();

        /* Test if the initial state of the second map is correct */
        map.loadMap(2);
        map.displayMap(map.findStart());
        System.out.println();

        /* Reveal entire map and then display it */
        for (int i = 0; i < MAP_SIZE; ++i) {
            for (int j = 0; j < MAP_SIZE; ++j) {
                map.reveal(new Point(i, j));
            }
        }
        map.displayMap(map.findStart());
        System.out.println();

        /* Test if the initial state of the third map is correct */
        map.loadMap(3);
        map.displayMap(map.findStart());
        System.out.println();

        /* Reveal entire map and then display it */
        for (int i = 0; i < MAP_SIZE; ++i) {
            for (int j = 0; j < MAP_SIZE; ++j) {
                map.reveal(new Point(i, j));
            }
        }
        map.displayMap(map.findStart());
        System.out.println();

        System.out.println("The character returned was " + map.getCharAtLoc(new Point(4, 4)));
        map.removeCharAtLoc(new Point(4, 4));
        map.displayMap(map.findStart());
        System.out.println("The character returned was " + map.getCharAtLoc(new Point(4, 4)));
    }

    /**
     * Loads the map with a given level
     *
     * @param mapNum the level of the map to be loaded
     */
    public void loadMap(int mapNum) {
        /* currentMap is bounded between [1, 3] */
        int currentMap = (mapNum % 4);
        if (currentMap == 0) {
            currentMap++;
        }

        try {
            Scanner read = new Scanner(new File("Map" + currentMap + ".txt"));
            int rowIndex = 0;
            do {
                String[] tokens = read.nextLine().split(" ");
                for (int i = 0; i < map.length; ++i) {
                    char c = tokens[i].charAt(0);
                    map[rowIndex][i] = c;
                    revealed[rowIndex][i] = false;
                }
                rowIndex++;
            }
            while (read.hasNext());

        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found");
        }
    }

    /**
     * Get the character located at the point's position
     *
     * @param p the position of the point that is to be revealed
     * @return the character at the point's position
     */
    public char getCharAtLoc(Point p) {
        return map[p.x][p.y];
    }

    /**
     * Display the content of the map
     *
     * @param p the point that will be indicated by a '*'
     */
    public void displayMap(Point p) {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                if (i == p.x && j == p.y) {
                    System.out.print("* ");
                } else if (!revealed[i][j]) {
                    System.out.print("X ");
                } else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Retrieve starting location of the map
     *
     * @return the Point which is the starting location of the map
     */
    public Point findStart() {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                if (map[i][j] == 's') {
                    return new Point(i, j);
                }
            }
        }
        throw new RuntimeException("Starting location not found");
    }

    /**
     * Reveal the location of the point
     *
     * @param p the location of the point that is to be revealed
     */
    public void reveal(Point p) {
        revealed[p.x][p.y] = true;
    }

    /**
     * Remove the character at the location
     *
     * @param p the point of the character to be removed
     */
    public void removeCharAtLoc(Point p) {
        map[p.x][p.y] = 'n';
    }
}
