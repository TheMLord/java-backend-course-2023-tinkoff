package edu.hw6.Task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An associative array storing key-value pairs on a hard disk. Task1
 * <p>
 * When creating a class object, data is loaded from a file into a buffer with validation.
 * It is possible to work with DiskMap data as with Map.
 * But it is very important to understand that buffer changes must be saved by calling the save Data On Disk Map method,
 * otherwise the changes will not be written to the hard disk
 */
public final class DiskMap implements Map<String, String> {
    private static final String PATH_IS_NOT_FILE_EXCEPTION = "The passed path is not the path to the file";
    private static final String INVALID_DATA_FILE =
        "The data format in the file does not match the template - key:value";
    private static final String SUCCESSFUL_SAVE_MESSAGE = "Data recorded successfully recorded in a file ";
    private static final String UNSUCCESSFUL_SAVE_MESSAGE = "Data could not be saved";
    private static final String KEY_VALUE_SEPARATOR = ":";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private File diskMapFile;
    private Map<String, String> dataFile;

    /**
     * Class constructor.
     *
     * @param diskMapFilePath the path to an already existing file or file that you want to create for recording
     * @throws DiskMapException errors that may occur when reading, writing, or creating a file.
     */
    public DiskMap(String diskMapFilePath) throws DiskMapException {
        this.diskMapFile = prepareFile(diskMapFilePath);
        this.dataFile = loadDataMap();
    }

    /**
     * Method of preparing the file for DiskMap.
     * <p>
     * if necessary, a file is created according to the path passed to the constructor, a record will be recorded there.
     * If the file has already been created, then this file is simply returned.
     * If a file creation error occurs or the passed path is not the path to the file,
     * an exception will be returned with the corresponding message
     *
     * @param filePath path to DiskMap file.
     * @return a ready-to-work object of the File class
     * @throws DiskMapException if the passed path cannot be created or is incorrect,
     *                          an error is returned with the corresponding message
     */
    private File prepareFile(String filePath) throws DiskMapException {
        File file = new File(filePath);
        try {
            var isCreated = file.createNewFile();
        } catch (IOException e) {
            throw new DiskMapException(e.getMessage());
        }

        if (file.isFile()) {
            return file;
        }
        throw new DiskMapException(PATH_IS_NOT_FILE_EXCEPTION);
    }

    /**
     * Method of downloading information that is available on the hard disk.
     * The file that the program is working with is being read,
     * data from it is being read into a temporary buffer that is represented by Map.
     * Data validation takes place in the method
     *
     * @return data from the disk, if the disk is empty, an empty buffer will be returned.
     * @throws DiskMapException if the data in the file turns out to be invalid, an exception will occur.
     */
    private Map<String, String> loadDataMap() throws DiskMapException {
        try (BufferedReader bfr = new BufferedReader(new FileReader(this.diskMapFile))) {
            Map<String, String> dataMap = new HashMap<>();

            String dataLine;
            while ((dataLine = bfr.readLine()) != null) {
                var keyValue = dataLine.split(KEY_VALUE_SEPARATOR);
                if (keyValue.length != 2) {
                    throw new DiskMapException(INVALID_DATA_FILE);
                }
                dataMap.put(keyValue[KEY_INDEX], keyValue[VALUE_INDEX]);
            }
            return dataMap;
        } catch (IOException | DiskMapException e) {
            throw new DiskMapException(e.getMessage());
        }
    }

    /**
     * Saving local DiskMap changes to disk.
     */
    public String saveDataOnDiskMap() {
        try (BufferedWriter bfr = new BufferedWriter(new FileWriter(this.diskMapFile))) {
            for (var data : this.dataFile.entrySet()) {
                bfr.write(data.getKey() + KEY_VALUE_SEPARATOR + data.getValue());
                bfr.newLine();
            }
            bfr.flush();
            return SUCCESSFUL_SAVE_MESSAGE + this.diskMapFile.toString();
        } catch (IOException e) {
            return UNSUCCESSFUL_SAVE_MESSAGE;
        }
    }

    @Override
    public int size() {
        return this.dataFile.size();
    }

    @Override
    public boolean isEmpty() {
        return this.dataFile.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.dataFile.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.dataFile.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return this.dataFile.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        return this.dataFile.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return this.dataFile.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        this.dataFile.putAll(m);
    }

    @Override
    public void clear() {
        this.dataFile.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return this.dataFile.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return this.dataFile.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return this.dataFile.entrySet();
    }
}
