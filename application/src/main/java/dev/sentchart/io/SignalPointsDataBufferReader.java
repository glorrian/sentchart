package dev.sentchart.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * <p>A class for working with files received on AFM. The file object is passed in the constructor,
 * after which a reading buffer of the standard size 8192 is created. The values from the file are read in pairs as floating point numbers.
 * Several data recording formats and file formats for reading are supported.</p>
 * Data formats:
 * <ul>
 * <li>NUMBER<b>,</b> NUMBER</li>
 * <li>NUMBER NUMBER (it can be any number of space between the numbers)</li>
 * <li>NUMBER<b>\t</b>NUMBER</li>
 * <li>NUMBER<b>;</b> NUMBER</li>
 * </ul>
 * <p>Each pair must be separated by a "\n". In the case 1 numbers cannot contains a comma separator for an obviously reason.
 * All numbers may contain "e" or "E" symbol as a designation for raising 10 to some degree.
 * Number after "e" obviously has an integer value.</p>
 * All supported file formats contains in {@link SignalPointsDataBufferReader#FILE_FORMATS}
 *
 * <p><b>Please be careful with null references when using methods in this class.
 * There is no way to refuse this implementation due to flexible file validation,
 * as this would lead to an excessive number of errors overloading the methods.</b></p>
 *
 * @implNote It is recommended to use {@link #readAll()} for small files and sequential reading through {@link #nextLine()} when the file is large
 */
public class SignalPointsDataBufferReader implements DataBufferReader<SignalPoint> {
    public static final List<String> FILE_FORMATS = List.of("*.csv", "*.txt");

    private BufferedReader dataBuffer;

    /**
     * The constructor creates a reading buffer for working with data from a file
     *
     * @param dataFile {@link File} object of a file with some data from AFM
     * @throws UnsupportedFileFormatException it is thrown out if the file extension is not supported.
     * All supported extensions are stored in {@link #FILE_FORMATS}
     */
    public SignalPointsDataBufferReader(File dataFile) throws IOException {
        String fileName = dataFile.getName();
        String fileExtension = "*" + fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!FILE_FORMATS.contains(fileExtension)) {
            throw new UnsupportedFileFormatException(
                    "The extension of file %s is not supported.".formatted(dataFile.getAbsolutePath()) +
                            " The supported extensions are reordered in ChartsDataBuffer.FILE_FORMATS");
        }
        dataBuffer = new BufferedReader(new FileReader(dataFile));
        if (nextLine().containsNull()) {
            throw new UnsupportedFileFormatException("Not a single line valid for the SignalPoint format was found in this file %s.".formatted(dataFile.getAbsolutePath()));
        }
        dataBuffer.mark(0);
    }

    /**
     * Returns a list with all pairs of values from the files.
     * The pairs are sorted by keys in natural order.
     * The list does not contain null, and pairs cannot contain null either in the key or in the value.
     * @implNote It is not recommended to use this method for large files.
     * For large files, it is better to use sequential processing of values via {@link SignalPointsDataBufferReader#nextLine()} to avoid memory overflow
     * @return A list of sorted in natural order pairs with values from file.
     */
    @Override
    public List<SignalPoint> readAll() throws IOException {
        dataBuffer.reset();
       return dataBuffer.lines()
               .map(this::getSignal)
               .filter(Objects::nonNull)
               .filter((x) -> !x.containsNull())
               .sorted(Comparator.naturalOrder()).toList();
    }

    /**
     * The method searches for the nearest valid string with values. If the lines in the file have ended, returns null
     * @return A pair of values from the nearest valid string with values
     */
    @Override
    public SignalPoint nextLine() throws IOException {
        String line = dataBuffer.readLine();
        if (line == null) {
            return null;
        }
        SignalPoint signalPoint = getSignal(line);
        while (signalPoint == null || signalPoint.containsNull()) {
            if (line == null) {
                return null;
            }
            line = dataBuffer.readLine();
            signalPoint = getSignal(line);
        }
        return signalPoint;
    }

    /**
     * Reset the stream to the beginning of the file
     */
    @Override
    public void reset() throws IOException {
        dataBuffer.reset();
    }

    @Override
    public void close() throws IOException {
        if (dataBuffer == null) {
            return;
        }
        try {
            dataBuffer.close();
        } finally {
            dataBuffer = null;
        }
    }

    private SignalPoint getSignal(String line){
        line = line.toLowerCase();
        if (line.contains(";")) {
            return getSignal(line, ";");
        }
        if (line.contains("\t")) {
            return getSignal(line, "\t");
        }
        long commaNumber = line.chars().filter(ch -> ch == ',').count();
       if (commaNumber == 3) {
           line = line.replaceAll(" ", "");
           String[] lineArray = line.split(",");
           if (lineArray.length != 4) {
               return new SignalPoint(
                       toNumber(lineArray[0] + "." + lineArray[1]),
                       toNumber(lineArray[2] + "." + lineArray[3])
               );
           }
       }
        if (commaNumber == 1) {
            return getSignal(line, ",");
        } else {
            return getSignal(line, " ");
        }
    }

    private SignalPoint getSignal(String line, String separator){
        if (separator.equals(" ")) {
            int separatorIndex = line.indexOf(separator);
            if (separatorIndex != -1) {
                return new SignalPoint(
                        toNumber(line.substring(0, separatorIndex)),
                        toNumber(line.replaceAll(" ", "").substring(separatorIndex))
                );
            } else {
                return null;
            }
        }
        line = line.replaceAll(" ", "");
        int separatorIndex = line.lastIndexOf(separator);
        if (separatorIndex != -1) {
            return new SignalPoint(
                    toNumber(line.substring(0, separatorIndex)),
                    toNumber(line.substring(separatorIndex+1))
            );
        } else {
            return null;
        }
    }

    private Double toNumber(String number){
        number = number.replaceAll(",", ".");
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException exception){
            return null;
        }
    }
}
