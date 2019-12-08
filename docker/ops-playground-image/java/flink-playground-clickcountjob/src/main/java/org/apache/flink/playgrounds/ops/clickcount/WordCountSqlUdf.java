package org.apache.flink.playgrounds.ops.clickcount;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.playgrounds.ops.clickcount.functions.StringToSite;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jianyuan
 * @version WordCountSqlUdf.java, v0.1 2019-12-08 9:18 下午 by jianyuan
 */
public class WordCountSqlUdf {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = BatchTableEnvironment.create(env);

        tEnv.registerFunction("StringToSite", new StringToSite(".com"));
        String words = "hello flink hello imooc";

        String[] split = words.split("\\W+");
        List<WC> wordList  = Arrays.stream(split).map(WC::new).collect(Collectors.toList());

        DataSet<WC> input = env.fromCollection(wordList);

        tEnv.registerDataSet("wordCount", input, "word, frequency");
        String sql = "select StringToSite(word) as word, sum(frequency) as frequency from wordCount group by word";

        Table table = tEnv.sqlQuery(sql);

        DataSet<WC> wcDataSet = tEnv.toDataSet(table, WC.class);
        wcDataSet.print();
    }

    public static class WC {
        private String word;
        private int    frequency;

        public WC() {
        }

        public WC(String word) {
            this(word, 1);
        }

        public WC(String word, int count) {
            this.word = word;
            this.frequency = count;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }
            WC wc = (WC) o;
            return frequency == wc.frequency &&
                    Objects.equals(word, wc.word);
        }

        @Override
        public int hashCode() {
            return Objects.hash(word, frequency);
        }

        @Override
        public String toString() {
            return "WC{" +
                    "word='" + word + '\'' +
                    ", count=" + frequency +
                    '}';
        }
    }
}


