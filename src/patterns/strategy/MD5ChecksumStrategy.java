package patterns.strategy;

import util.ChecksumUtil;

public class MD5ChecksumStrategy implements ChecksumStrategy {

    @Override
    public String calculate(String filePath) throws Exception {
        return ChecksumUtil.calculateMD5(filePath);
    }
}
