import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.util.HashSet;

public class UnionMapReduce {

    public static class UnionMapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text word = new Text();
        private HashSet<String> uniqueWords = new HashSet<>();

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String wordAsString = value.toString();

            if (uniqueWords.add(wordAsString)) {
                word.set(wordAsString);
                context.write(word, new Text("1"));
            }
        }
    }

    public static class UnionReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            context.write(key, new Text());
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "UnionMapReduce");
        job.setJarByClass(UnionMapReduce.class);

        job.setMapperClass(UnionMapper.class);
        job.setReducerClass(UnionReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path("/headless/Desktop/workspace/DigitSet/numbers_1.txt"));
        FileInputFormat.addInputPath(job, new Path("/headless/Desktop/workspace/DigitSet/numbers_2.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/headless/Desktop/workspace/DigitSet/data/Union"));

        boolean success = job.waitForCompletion(true);
        if (success) {
            System.out.println("Job completed successfully!");
        } else {
            System.err.println("Job failed!");
        }
        //System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

