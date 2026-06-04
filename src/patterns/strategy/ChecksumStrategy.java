package patterns.strategy;

public interface ChecksumStrategy {

    String calculate(String filePath) throws Exception;
}
