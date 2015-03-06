package com.framework.hadoop;
/*package com.bucry.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.JobConf;
import org.junit.Test;

import com.bucry.core.hadoop.MapReduceMapper;
import com.bucry.core.hadoop.MapReduceReducer;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

public class MapReduceTest {
	@Test
	public void hadoopFileTests() {
		
	}
	
	private void fileTests(String[] files) {
		if (files.length != 2) {
			System.err.println("file error");
			System.exit(-1);
		}
		
		JobConf conf = new JobConf(MapReduceMapper.class);
		conf.setJobName("maper");
		
		//FileInputFormat.addInputPath(conf, new Path(files[0]));
		//FileOutputFormat.setOutputCompressorClass(conf, new Path(files[1]));
		conf.setMapperClass(MapReduceMapper.class);
		conf.setReducerClass(MapReduceReducer.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		//JobClient.runJob(conf);
		
	}

}
*/