package org.apache.flink.playgrounds.ops.clickcount.functions;

import org.apache.flink.table.functions.ScalarFunction;

/**
 * @author jianyuan
 * @version : StringToSite.java, v0.1 2019-12-08 9:16 下午 by jianyuan
 */
public class StringToSite extends ScalarFunction {
    private String address;

    public StringToSite(String address) {
        this.address = address;
    }

    public String eval(String s) {
        return s + "." + address;
    }
}
